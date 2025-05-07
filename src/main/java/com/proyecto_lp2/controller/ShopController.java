package com.proyecto_lp2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto_lp2.model.Categoria;
import com.proyecto_lp2.repository.ICategoriaRepository;
import com.proyecto_lp2.repository.IProductoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ShopController {

	@Autowired
	ICategoriaRepository icate;

	@Autowired
	IProductoRepository iprod;

	@GetMapping("/shop")
	public String shop(Model model, HttpSession session) {
		model.addAttribute("lstCategorias", icate.findAll());
		model.addAttribute("lstProductos", iprod.findAll());
		model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));

		return "shop";
	}

	@PostMapping("/filto/categorias")
	public String filtro(@RequestParam Categoria id, Model model, HttpSession session) {
		model.addAttribute("lstCategorias", icate.findAll());

		if (id == null) {
			model.addAttribute("lstProductos", iprod.findAll());
			model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		} else {
			model.addAttribute("lstProductos", iprod.findByIdcategoria(id));
			model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		}

		return "shop";
	}

	@GetMapping("/filtro/{id}")
	public String filtroLinks(@PathVariable Categoria id, Model model, HttpSession session) {
		model.addAttribute("lstCategorias", icate.findAll());
		if (id == null) {
			model.addAttribute("lstProductos", iprod.findAll());
			model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		} else {
			model.addAttribute("lstProductos", iprod.findByIdcategoria(id));
			model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		}
		return "shop";
	}

}
