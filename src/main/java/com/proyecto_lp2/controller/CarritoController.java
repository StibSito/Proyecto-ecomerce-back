package com.proyecto_lp2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto_lp2.model.Carrito;
import com.proyecto_lp2.model.Cuenta;
import com.proyecto_lp2.model.DetalleBoleta;
import com.proyecto_lp2.model.Producto;
import com.proyecto_lp2.repository.IProductoRepository;
import com.proyecto_lp2.service.CarritoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CarritoController {

	@Autowired
	private IProductoRepository iprod;

	@Autowired
	private CarritoService carritoService;

	@GetMapping("/carrito")
	public String mostrarCarrito(HttpSession session, Model model) {
		model.addAttribute("carro", carritoService.obtenerCarrito(session));
		model.addAttribute("subTotalVenta", session.getAttribute("subTotalVenta"));
		model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		return "cart";
	}

	@PostMapping("/carrito/agregar")
	public String agregarProductoCarrito(@RequestParam String idprod, @RequestParam int cantidad, HttpSession session,
			Model model) {
		Producto producto = iprod.findById(idprod).orElse(null);

		// Verificar si hay una cuenta logueada
		Cuenta cuenta = (Cuenta) session.getAttribute("cuentaLogeada");
		Integer idcuenta = cuenta != null ? cuenta.getIdcuenta() : null;

		// Obtener o inicializar el carrito en la sesión
		@SuppressWarnings("unchecked")
		List<DetalleBoleta> carro = (List<DetalleBoleta>) session.getAttribute("carro");
		if (carro == null) {
			carro = new ArrayList<>();
			session.setAttribute("carro", carro);
		}

		// Buscar el producto en el carrito
		DetalleBoleta productoExistente = null;
		for (DetalleBoleta detalle : carro) {
			if (detalle.getIdprod().equals(producto.getIdprod())) {
				productoExistente = detalle;
				break;
			}
		}

		if (productoExistente != null) {
			// Si el producto ya existe en el carrito, actualiza la cantidad y el precio
			productoExistente.setCantidad(productoExistente.getCantidad() + cantidad);
			productoExistente.setPreciovta(producto.getPrecio()); // Actualiza el precio si ha cambiado
		} else {
			// Crear el detalle del producto a añadir
			DetalleBoleta detalle = new DetalleBoleta();
			detalle.setIdprod(producto.getIdprod());
			detalle.setObjProducto(producto);
			detalle.setCantidad(cantidad);
			detalle.setPreciovta(producto.getPrecio());
			carro.add(detalle);
		}

		// Guardar en la base de datos solo si el usuario está logueado
		if (idcuenta != null) {
			Carrito carritoExistente = carritoService.findByIdcuentaAndIdprod(idcuenta, idprod);
			if (carritoExistente == null) {
				// Crear nuevo carrito si no existe el producto en el carrito del usuario
				Carrito nuevoCarrito = new Carrito();
				nuevoCarrito.setIdprod(idprod);
				nuevoCarrito.setIdcuenta(idcuenta);
				nuevoCarrito.setQuantity(cantidad);
				nuevoCarrito.setPrice(producto.getPrecio());
				carritoService.save(nuevoCarrito);
			} else {
				// Si ya existe el producto en el carrito del usuario, actualiza la cantidad
				carritoExistente.setQuantity(carritoExistente.getQuantity() + cantidad);
				carritoService.save(carritoExistente);
			}
		}

		actualizarTotales(session, carro);

		return "redirect:/carrito";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/aumentar")
	public String aumentarCantidad(@RequestParam int cantidad, HttpSession session) {
		List<DetalleBoleta> carro = (List<DetalleBoleta>) session.getAttribute("carro");
		Cuenta cuenta = (Cuenta) session.getAttribute("cuentaLogeada");
		DetalleBoleta detalle = carro.get(cantidad);
		if (carro != null && cuenta != null && cantidad >= 0 && cantidad < carro.size()) {
			

			detalle.setCantidad(detalle.getCantidad() + 1);
			detalle.setPreciovta(detalle.getObjProducto().getPrecio());

			carritoService.actualizarCantidadProducto(cuenta.getIdcuenta(), detalle.getIdprod(), detalle.getCantidad());
		}else {
			detalle.setCantidad(detalle.getCantidad() + 1);
			detalle.setPreciovta(detalle.getObjProducto().getPrecio());
		}
		actualizarTotales(session, carro);

		return "redirect:/carrito";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/disminuir")
	public String disminuirCantidad(@RequestParam int cantidad, HttpSession session) {
		List<DetalleBoleta> carro = (List<DetalleBoleta>) session.getAttribute("carro");
		Cuenta cuenta = (Cuenta) session.getAttribute("cuentaLogeada");
		DetalleBoleta detalle = carro.get(cantidad);
		if (carro != null && cuenta != null && cantidad >= 0 && cantidad < carro.size()) {

			if (detalle.getCantidad() > 1) {
				detalle.setCantidad(detalle.getCantidad() - 1);
				detalle.setPreciovta(detalle.getObjProducto().getPrecio());

				carritoService.actualizarCantidadProducto(cuenta.getIdcuenta(), detalle.getIdprod(),
						detalle.getCantidad());
			}
		} else {
			if (detalle.getCantidad() > 1) {
				detalle.setCantidad(detalle.getCantidad() - 1);
				detalle.setPreciovta(detalle.getObjProducto().getPrecio());

			}
		}

		actualizarTotales(session, carro);

		return "redirect:/carrito";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/remove/{idprod}")
	public String eliminarDelCarrito(@PathVariable String idprod, HttpSession session) {
		// Recuperar el carrito y la cuenta desde la sesión
		List<DetalleBoleta> carro = (List<DetalleBoleta>) session.getAttribute("carro");
		Cuenta cuenta = (Cuenta) session.getAttribute("cuentaLogeada");

		// Verificar si la cuenta y el carrito son válidos
		if (cuenta != null && carro != null) {
			int idcuenta = cuenta.getIdcuenta();
			DetalleBoleta productoAEliminar = null;

			// Buscar el producto en el carrito
			for (DetalleBoleta detalle : carro) {
				if (detalle.getIdprod().equals(idprod)) {
					productoAEliminar = detalle;
					break;
				}
			}

			// Si se encontró el producto, eliminarlo de la lista y la base de datos
			if (productoAEliminar != null) {
				carro.remove(productoAEliminar); // Eliminar del carrito en la sesión
				carritoService.eliminarProducto(idcuenta, idprod); // Eliminar de la base de datos
			}

			// Actualizar los totales del carrito en la sesión
			actualizarTotales(session, carro);
		}

		return "redirect:/carrito";
	}

	private void actualizarTotales(HttpSession session, List<DetalleBoleta> carro) {
		int cantArticulos = 0;
		double subTotalVenta = 0.0;

		for (DetalleBoleta detalle : carro) {
			cantArticulos += detalle.getCantidad();
			subTotalVenta += detalle.getImporte();
		}

		session.setAttribute("carro", carro);
		session.setAttribute("cantArticulos", cantArticulos);
		session.setAttribute("subTotalVenta", subTotalVenta);
	}

}
