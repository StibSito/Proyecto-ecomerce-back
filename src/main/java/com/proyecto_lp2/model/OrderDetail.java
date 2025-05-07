package com.proyecto_lp2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_order_detail")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int idorder;
	private String idprod;
	private int cantidad;
	private double preciovta;

	@ManyToOne
	@JoinColumn(name = "idorder", updatable = false, insertable = false)
	private Order objOrder;

	@ManyToOne
	@JoinColumn(name = "idprod", insertable = false, updatable = false)
	private Producto objProducto;

}
