package com.proyecto_lp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_lp2.model.Estado;

@Repository
public interface IEstadosRepository extends JpaRepository<Estado, Integer>{

}
