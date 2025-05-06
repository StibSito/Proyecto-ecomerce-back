package com.proyecto_lp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto_lp2.model.Categoria;
import com.proyecto_lp2.repository.ICategoriaRepository;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaController {
	
	@Autowired
	private ICategoriaRepository categoriaRepository;
	
	@GetMapping()
	public ResponseEntity<List<Categoria>> findAllCategories() {
		List<Categoria> categorias = categoriaRepository.findAll();
		if(categorias.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(categorias);
	}
}
