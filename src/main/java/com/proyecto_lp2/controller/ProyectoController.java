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

@Controller
public class ProyectoController {

	@Autowired
	ICategoriaRepository icate;

	@Autowired
	IProductoRepository iprod;

	@Autowired
	IEstadosRepository iest;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("lstCategorias", icate.findAll()); 
		model.addAttribute("lstProductos", iprod.findAll());

		return "index"; 
	}

	@GetMapping("/about")
	public String about() {
		return "about"; 
	}

	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}

	@GetMapping("/gestionProductos")
	public String mostrarGestionProductos(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("lstProductos", iprod.findAll());
		model.addAttribute("lstCategorias", icate.findAll());
		model.addAttribute("lstEstados", iest.findAll());
		return "product-management";
	}

	@GetMapping("/gracias")
	public String gracias() {
		return "thankyou";
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	
	


	@GetMapping("/checkout")
	public String checkout() {
		return "checkout";
	}

	@GetMapping("/registrar")
	public String registrar(Model model) {
		model.addAttribute("cuenta", new Cuenta());
		return "register";
	}

	@GetMapping("/contacto")
	public String contacto() {
		return "contact";
	}

}
