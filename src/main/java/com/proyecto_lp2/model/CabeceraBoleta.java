package com.proyecto_lp2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_cab_boleta")
public class CabeceraBoleta {

	@Id
	private String num_bol;
	private String fch_bol;
	private int cod_cliente;
}
