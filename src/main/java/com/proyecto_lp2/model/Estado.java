package com.proyecto_lp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_estados")
public class Estado {

	@Id
	@Column(name = "idestado")
	private int estado;
	private String descripcion;
}
