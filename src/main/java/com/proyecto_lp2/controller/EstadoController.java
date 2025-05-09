package com.proyecto_lp2.controller;

import java.util.List;
import java.util.Optional;
import com.proyecto_lp2.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto_lp2.model.Estado;
import com.proyecto_lp2.repository.IEstadosRepository;

@RestController
@RequestMapping("/api/estados")
@CrossOrigin(origins = "http://localhost:4200")
public class EstadoController {

    private final SecurityConfig securityConfig;

    @Autowired
    private IEstadosRepository estadosRepository;

    EstadoController(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }


    @GetMapping()
    public ResponseEntity<List<Estado>> findAllEstados() {
        List<Estado> estados = estadosRepository.findAll();
        if (estados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(estados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> findEstadoById(@PathVariable("id") int id) {
        Optional<Estado> estado = estadosRepository.findById(id);
        if (!estado.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(estado.get());
    }

    @PostMapping()
    public ResponseEntity<Estado> createEstado(@RequestBody Estado estado) {
        try {
            Estado savedEstado = estadosRepository.save(estado);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEstado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> updateEstado(@PathVariable int id, @RequestBody Estado estado) {
        Optional<Estado> existingEstado = estadosRepository.findById(id);
        if (!existingEstado.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        estado.setId(id); 
        Estado updatedEstado = estadosRepository.save(estado);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEstado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstado(@PathVariable int id) {
        Optional<Estado> estado = estadosRepository.findById(id);
        if (!estado.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        estadosRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
