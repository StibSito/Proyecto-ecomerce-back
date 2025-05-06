package com.proyecto_lp2.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class DetalleBoletaId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String num_bol;
	private String idprod;
}
