package com.proyecto_lp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto_lp2.model.Categoria;
import com.proyecto_lp2.model.Producto;
import com.proyecto_lp2.repository.ICategoriaRepository;
import com.proyecto_lp2.repository.IProductoRepository;

@RestController
@RequestMapping("/api/shop")
@CrossOrigin(origins = "http://localhost:4200") 
public class ShopController {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @PostMapping("/filto/categorias")
    public ResponseEntity<List<Producto>> filtro(@RequestParam(required = false) int categoriaId) {
        if (categoriaId == 0) {
            List<Producto> productos = productoRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(productos);
        } else {
            List<Producto> productos = productoRepository.findByIdcategoria(categoriaId); 
            return ResponseEntity.status(HttpStatus.OK).body(productos);
        }
    }

    @GetMapping("/filtro/{id}")
    public ResponseEntity<List<Producto>> filtroLinks(@PathVariable int id) {
        List<Producto> productos = productoRepository.findByIdcategoria(id);
        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(productos);
    }
}
