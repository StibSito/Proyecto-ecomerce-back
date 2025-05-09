package com.proyecto_lp2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto_lp2.model.Categoria;
import com.proyecto_lp2.repository.ICategoriaRepository;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaController {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    // Get all categories
    @GetMapping()
    public ResponseEntity<List<Categoria>> findAllCategories() {
        List<Categoria> categorias = categoriaRepository.findAll();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findCategoryById(@PathVariable("id") int id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (!categoria.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoria.get());
    }

    // Create new category
    @PostMapping()
    public ResponseEntity<Categoria> createCategory(@RequestBody Categoria categoria) {
        try {
            Categoria savedCategoria = categoriaRepository.save(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update existing category
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategory(@PathVariable("id") int id, @RequestBody Categoria categoria) {
        Optional<Categoria> existingCategoria = categoriaRepository.findById(id);
        if (!existingCategoria.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        categoria.setId(id); // Set the ID to the existing category ID (to update it)
        Categoria updatedCategoria = categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategoria);
    }

    // Delete category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (!categoria.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        categoriaRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
