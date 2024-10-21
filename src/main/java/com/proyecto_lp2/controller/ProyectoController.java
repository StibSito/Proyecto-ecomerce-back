package com.proyecto_lp2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
		model.addAttribute("lstCategorias", icate.findAll()); // icate es tu servicio de categorías
		model.addAttribute("lstProductos", iprod.findAll());

		return "index"; // Retorna la vista principal, que incluye el fragmento
	}

	@GetMapping("/about")
	public String about() {
		return "about"; // about.html
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
		return "thankyou"; // nombre de la plantilla HTML sin la extensión
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login"; // Devuelve el nombre de la vista (login.html)
	}

	@GetMapping("/checkout")
	public String checkout() {
		return "checkout";
	}

	@GetMapping("/registrar")
	public String registrar() {
		return "register";
	}

	@GetMapping("/contacto")
	public String contacto() {
		return "contact";
	}

}
