package com.proyecto_lp2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto_lp2.repository.ICategoriaRepository;
import com.proyecto_lp2.repository.IProductoRepository;

@Controller
public class ShopController {

	@Autowired
	ICategoriaRepository icate;

	@Autowired
	IProductoRepository iprod;

	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("lstCategorias", icate.findAll());
		model.addAttribute("lstProductos", iprod.findAll());
		return "shop";
	}

	@PostMapping("/filto/categorias")
	public String filtro(@RequestParam int id, Model model) {
		model.addAttribute("lstCategorias", icate.findAll());
		if (id == 0) {
			model.addAttribute("lstProductos", iprod.findAll());
		} else {
			model.addAttribute("lstProductos", iprod.findByIdcategoria(id));
		}

		return "shop";
	}

	@GetMapping("/filtro/{id}")
	public String filtroLinks(@PathVariable int id, Model model) {
		model.addAttribute("lstCategorias", icate.findAll());
		if (id == 0) {
			model.addAttribute("lstProductos", iprod.findAll());
		} else {
			model.addAttribute("lstProductos", iprod.findByIdcategoria(id));
		}
		return "shop";
	}

}
