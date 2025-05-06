package com.proyecto_lp2.model;

import jakarta.persistence.Column;
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
@Table(name = "tb_ordenes")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int idorder;
	private int idcuenta;
	private double total;
	private String date;

	@ManyToOne
	@JoinColumn(name = "idcuenta", insertable = false, updatable = false)
	private Cuenta objCuenta;
}
