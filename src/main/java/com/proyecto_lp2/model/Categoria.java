package com.proyecto_lp2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_categorias")
public class Categoria {

	@Id
	private int id;
	private String descripcion;
}
