package com.proyecto_lp2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto_lp2.model.Cuenta;
import com.proyecto_lp2.repository.ICuentaRepository;

@Controller
public class CuentaController {

	@Autowired
	ICuentaRepository icuen;

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

		model.addAttribute("mensaje", "Cuenta registrada exitosamente, inicie sesión");
		model.addAttribute("csmensaje", "alert alert-success");

		return "register";
	}

	@PostMapping("/auth/login")
	public String postMethodName(@RequestParam String email, @RequestParam String clave, Model model) {

		Cuenta cuenta = icuen.findByEmailAndClave(email, clave);
		if (cuenta != null) {
			model.addAttribute("mensaje", "Bienvenido");
			model.addAttribute("cssmensaje", "alert alert-success");
			return "index";
		} else {
			// mensaje "Error"
			model.addAttribute("mensaje", "Usuario o clave erróneos");
			model.addAttribute("cssmensaje", "alert alert-danger");
			return "login";
		}

	}

}
