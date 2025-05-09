package com.proyecto_lp2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_lp2.model.Categoria;
import com.proyecto_lp2.model.Producto;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Integer>{

}
