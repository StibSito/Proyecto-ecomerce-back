package com.proyecto_lp2.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_lp2.model.Categoria;
import com.proyecto_lp2.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, String> {

	// encuentra el producto segun su categoria
	List<Producto> findByIdcategoria(Categoria idcategoria);

	// entre precios
	List<Producto> findByPrecioBetween(Double minPrice, Double maxPrice);

	//menores a 100
	List<Producto> findByPrecioLessThan(double precio);
	
	Page<Producto> findAll(Pageable pageable);

}
