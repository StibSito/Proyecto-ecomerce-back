package com.proyecto_lp2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.proyecto_lp2.model.Cuenta;
import com.proyecto_lp2.model.Producto;
import com.proyecto_lp2.repository.ICategoriaRepository;
import com.proyecto_lp2.repository.IEstadosRepository;
import com.proyecto_lp2.repository.IProductoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProyectoController {

	@Autowired
	ICategoriaRepository icate;

	@Autowired
	IProductoRepository iprod;

	@Autowired
	IEstadosRepository iest;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		model.addAttribute("lstCategorias", icate.findAll());
		model.addAttribute("lstProductos", iprod.findAll());
		model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		return "index";
	}

	@GetMapping("/about")
	public String about(Model model, HttpSession session) {
		model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		return "about";
	}

	

	@GetMapping("/gracias")
	public String gracias(Model model,HttpSession session) {
		model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		return "thankyou";
	}

	@GetMapping("/login")
	public String showLoginPage(Model model, HttpSession session) {
		model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		return "login";
	}



	@GetMapping("/contacto")
	public String contacto(Model model, HttpSession session) {
		model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		return "contact";
	}

	
	
	
}
