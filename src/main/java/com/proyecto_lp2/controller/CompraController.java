package com.proyecto_lp2.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.proyecto_lp2.model.Cuenta;
import com.proyecto_lp2.model.DetalleBoleta;

import jakarta.servlet.http.HttpSession;

@Controller
public class CompraController {

	@GetMapping("/pasarCaja")
	public String checkout(Model model, HttpSession session) {
		Cuenta cuenta = (Cuenta) session.getAttribute("cuentaLogeada");
		List<DetalleBoleta> carro = (List<DetalleBoleta>) session.getAttribute("carro");
		model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		model.addAttribute("subTotalVenta", session.getAttribute("subTotalVenta"));

		// enviar a la pagina
		model.addAttribute("carro", carro);
		model.addAttribute("cuenta", cuenta);

		return "checkout";
	}

}
