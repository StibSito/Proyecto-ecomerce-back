package com.proyecto_lp2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_det_boleta")
@IdClass(DetalleBoletaId.class)
public class DetalleBoleta {

	@Id
	private String num_bol;

	@Id
	private String idprod;

	private int cantidad;
	private double preciovta;

	@ManyToOne
	@JoinColumn(name = "idprod", updatable = false, insertable = false)
	private Producto objProducto;

	public double getImporte() {
		return cantidad * preciovta;
	}
}
