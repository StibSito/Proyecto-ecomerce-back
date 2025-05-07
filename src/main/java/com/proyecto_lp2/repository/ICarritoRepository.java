package com.proyecto_lp2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto_lp2.model.Carrito;

public interface ICarritoRepository extends JpaRepository<Carrito, Integer> {

	// select * from tb_carrito where idcuenta = 2;
	// devuelve una lista de carrito relacionada a una cuenta
	List<Carrito> findByIdcuenta(int idcuenta);

	// select * from tb_carrito where idcuenta = 2 AND idprod = "P0001";
	// devuelve un fila del carrito de una cuenta y producto especifico
	Carrito findByIdcuentaAndIdprod(int idcuenta, String idprod);

	@Modifying
	@Transactional
	@Query("UPDATE Carrito c SET c.quantity = :cantidad WHERE c.idcuenta = :idcuenta AND c.idprod = :idprod")
	void actualizarCantidadProducto(@Param("idcuenta") int idcuenta, @Param("idprod") String idprod,
			@Param("cantidad") int cantidad);


}
