package com.proyecto_lp2.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto_lp2.model.CabeceraBoleta;
import com.proyecto_lp2.model.Carrito;
import com.proyecto_lp2.model.Cuenta;
import com.proyecto_lp2.model.DetalleBoleta;
import com.proyecto_lp2.model.Order;
import com.proyecto_lp2.model.OrderDetail;
import com.proyecto_lp2.repository.ICarritoRepository;
import com.proyecto_lp2.service.VentaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class VentaController {

	@Autowired
	private VentaService ventaService;

	@Autowired
	private ICarritoRepository carritoRepository;

	@PostMapping("/finalizarCompra")
	public String finalizarCompra(HttpSession session, Model model) {
		CabeceraBoleta cabecera = new CabeceraBoleta();
		Cuenta cuenta = (Cuenta) session.getAttribute("cuentaLogeada");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		// Extrae información de la cuenta y otros datos requeridos de la sesión
		// Asume que la cuenta ya está en sesión
		cabecera.setCod_cliente(cuenta.getIdcuenta());
		cabecera.setFch_bol(dtf.format(now));

		// Obtiene el carrito de la sesión y convierte a detalles de boleta
		List<DetalleBoleta> detalles = (List<DetalleBoleta>) session.getAttribute("carro");

		List<Carrito> carrito = carritoRepository.findByIdcuenta(cuenta.getIdcuenta());

		int cantArticulos = session.getAttribute("cantArticulos") != null ? (int) session.getAttribute("cantArticulos")
				: 0;
		double subTotalVenta = session.getAttribute("subTotalVenta") != null
				? (double) session.getAttribute("subTotalVenta")
				: 0.0;

		Order o = new Order();
		o.setIdcuenta(cuenta.getIdcuenta());
		o.setTotal(subTotalVenta);
		o.setDate(dtf.format(now));
		ventaService.guardarOrder(o);
		cantArticulos = 0;
		subTotalVenta = 0.0;
		// Llama al servicio de venta
		try {
			ventaService.realizarVenta(cabecera, detalles);
			carritoRepository.deleteAll(carrito);

			for (Carrito item : carrito) {
				OrderDetail od = new OrderDetail();
				od.setIdorder(o.getIdorder());
				od.setIdprod(item.getIdprod());
				od.setCantidad(item.getQuantity());
				od.setPreciovta(item.getPrice());
				ventaService.guardarOrderDetail(od);
			}
			session.setAttribute("cantArticulos", cantArticulos);
			session.setAttribute("subTotalVenta", subTotalVenta);
			session.removeAttribute("carro");
			model.addAttribute("mensaje", "Compra realizada exitosamente con el código " + cabecera.getNumbol());
			model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		} catch (Exception e) {
			model.addAttribute("error", "Error al realizar la compra: " + e.getMessage());
		}

		return "thankyou";
	}

}
