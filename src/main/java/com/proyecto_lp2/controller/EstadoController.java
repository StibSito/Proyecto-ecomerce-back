package com.proyecto_lp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto_lp2.model.Estado;
import com.proyecto_lp2.repository.IEstadosRepository;

@RestController
@RequestMapping("/api/estados")
@CrossOrigin(origins = "http://localhost:4200")
public class EstadoController {

	
	@Autowired
	private IEstadosRepository estadosRepository;
	
	@GetMapping()
	public ResponseEntity<List<Estado>> findAllCategories() {
		List<Estado> estados = estadosRepository.findAll();
		if(estados.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(estados);
	}
}
