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
@Table(name = "tb_cuentas")
public class Cuenta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
	private int idcuenta;

	private String nombre;
	private String apellido;

	@Column(unique = true)
	private String email;
	private String celular;
	private String clave;
	private String fnacim;
	private int tipo;
	private int estado;

	@ManyToOne
	@JoinColumn(name = "tipo", insertable = false, updatable = false)
	private Tipo objTipo;

	@ManyToOne
	@JoinColumn(name = "estado", insertable = false, updatable = false)
	private Estado objEstado;

}
