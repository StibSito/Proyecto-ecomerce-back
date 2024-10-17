package com.proyecto_lp2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProyectoController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/about")
	public String about() {
	        return "about";  // about.html
	}
	
	@GetMapping("/cart")
	public String cart() {
	        return "cart";  // about.html
	    }
	

    @GetMapping("/gestionProductos")
    public String mostrarGestionProductos() {
        return "product-management"; // Nombre de la vista Thymeleaf (gestionProductos.html)
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
    
    @GetMapping("/gestor-productos")
    public String gestorproductos() {
        return "product-management"; 
    }
    

}
