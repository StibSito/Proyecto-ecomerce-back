package com.proyecto_lp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto_lp2.model.DetalleBoleta;
import com.proyecto_lp2.model.DetalleBoletaId;

public interface IDetalleBoletaRepository extends JpaRepository<DetalleBoleta, DetalleBoletaId> {

	
}
