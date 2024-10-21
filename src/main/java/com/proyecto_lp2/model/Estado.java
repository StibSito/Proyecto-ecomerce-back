package com.proyecto_lp2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_estados")
public class Estado {

	@Id
	private int idestado;
	private String descripcion;
}
