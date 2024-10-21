package com.proyecto_lp2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_det_boleta")
public class DetalleBoleta {

	@Id
	private String num_bol ;
	private String idprod  ;    
	private int cantidad ;  
	private double preciovta;  
	
}
