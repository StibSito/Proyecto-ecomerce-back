package com.proyecto_lp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_productos")
public class Producto {

	@Id
	private String idprod;
	private String descripcion;
	private int stock;
	private double precio;
	private int idcategoria;
	@Column(name = "estado")
	private int idestado;
	@ManyToOne
	@JoinColumn(name = "idcategoria",insertable = false,updatable = false)
	private Categoria categoria;
	@ManyToOne
	@JoinColumn(name = "estado",insertable = false,updatable = false)
	private Estado estado;

	

}
