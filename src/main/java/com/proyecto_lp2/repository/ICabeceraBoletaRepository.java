package com.proyecto_lp2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto_lp2.model.CabeceraBoleta;

public interface ICabeceraBoletaRepository extends JpaRepository<CabeceraBoleta, String> {

	Optional<CabeceraBoleta> findTopByOrderByNumbolDesc();

}
