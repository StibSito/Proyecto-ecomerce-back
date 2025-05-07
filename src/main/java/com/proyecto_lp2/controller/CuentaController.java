package com.proyecto_lp2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto_lp2.model.Carrito;
import com.proyecto_lp2.model.Cuenta;
import com.proyecto_lp2.model.DetalleBoleta;
import com.proyecto_lp2.model.Producto;
import com.proyecto_lp2.repository.ICuentaRepository;
import com.proyecto_lp2.repository.IProductoRepository;
import com.proyecto_lp2.service.CarritoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CuentaController {

	@Autowired
	private ICuentaRepository icuen;

	@Autowired
	private IProductoRepository iprod;

	@Autowired
	private CarritoService carritoService;

	@GetMapping("/registrar")
	public String registrar(Model model) {
		model.addAttribute("cuenta", new Cuenta());
		return "register";
	}

	@PostMapping("/registrar/cuenta")
	public String registrarCuenta(@ModelAttribute Cuenta cuenta, @RequestParam String repeatClave,
			@RequestParam String email, Model model) {

		Cuenta cuentaemail = icuen.findByEmail(email);

		// correo ya está registrado
		if (cuentaemail != null) {
			model.addAttribute("mensaje", "El correo ya está registrado");
			model.addAttribute("csmensaje", "alert alert-danger");
			return "register";
		}

		// contraseñas coinciden
		if (!cuenta.getClave().equals(repeatClave)) {
			model.addAttribute("mensaje", "Las contraseñas no coinciden");
			model.addAttribute("csmensaje", "alert alert-danger");
			return "register";
		}

		cuenta.setTipo(2);
		cuenta.setEstado(1);
		icuen.save(cuenta);

		model.addAttribute("cuenta", new Cuenta());
		model.addAttribute("mensaje", "Cuenta registrada exitosamente, inicie sesión");
		model.addAttribute("csmensaje", "alert alert-success");

		return "register";
	}

	@PostMapping("/auth/login")
	public String postMethodName(@RequestParam String email, @RequestParam String clave, Model model,
			HttpSession session) {

		Cuenta cuenta = icuen.findByEmailAndClave(email, clave);

		if (cuenta != null) {
			// Cargar carrito desde la base de datos
			List<Carrito> carritos = carritoService.findByIdcuenta(cuenta.getIdcuenta());
			List<DetalleBoleta> carro = new ArrayList<>();
			double subTotalVenta = 0.0;
			int cantidadTotalProductos = 0;

			// Convertir los carritos a DetalleBoleta
			for (Carrito carrito : carritos) {
				DetalleBoleta detalle = new DetalleBoleta();
				detalle.setIdprod(carrito.getIdprod());
				detalle.setCantidad(carrito.getQuantity());
				detalle.setPreciovta(carrito.getPrice());

				// Obtener el producto desde la base de datos y asignarlo al detalle
				Producto producto = iprod.findById(carrito.getIdprod()).orElse(null);
				detalle.setObjProducto(producto);

				carro.add(detalle);

				// Calcular subtotal y acumular cantidad total de productos
				subTotalVenta += detalle.getCantidad() * detalle.getPreciovta();
				cantidadTotalProductos += detalle.getCantidad(); // Acumular la cantidad
			}

			// Establecer atributos de sesión
			session.setAttribute("cuentaLogeada", cuenta);
			session.setAttribute("carro", carro);
			session.setAttribute("cantArticulos", cantidadTotalProductos); // Usar la cantidad total de productos
			session.setAttribute("subTotalVenta", subTotalVenta);
			model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
			model.addAttribute("mensaje", "Bienvenido");
			model.addAttribute("cssmensaje", "alert alert-success");
			return "index";
		} else {
			// Mensaje de error
			model.addAttribute("mensaje", "Usuario o clave erróneos");
			model.addAttribute("cssmensaje", "alert alert-danger");
			return "login";
		}
	}

	@GetMapping("/profile")
	public String perfil(Model model, HttpSession session) {
		Cuenta cuenta = (Cuenta) session.getAttribute("cuentaLogeada");
		if (cuenta != null) {
			model.addAttribute("cuenta", cuenta);
			model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
			return "profile-page";
		} else {
			return "redirect:/login";
		}

	}

	@GetMapping("/cerrarSesion")
	public String cerrar(Model model, HttpSession session) {
		session.invalidate();
		return "login";
	}

	@PostMapping("/cuenta/actualiza")
	public String actualizarCuenta(@ModelAttribute Cuenta cuenta, Model model, HttpSession session) {
		Cuenta cuentaLog = (Cuenta) session.getAttribute("cuentaLogeada");

		cuenta.setFnacim(cuentaLog.getFnacim());
		cuenta.setTipo(2);
		cuenta.setEstado(1);
		icuen.save(cuenta);
		return "login";
	}

}
