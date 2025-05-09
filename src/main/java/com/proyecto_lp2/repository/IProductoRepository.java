package com.proyecto_lp2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_lp2.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, String> {

    List<Producto> findByIdcategoria(int idcategoria);  

    List<Producto> findByPrecioBetween(Double minPrice, Double maxPrice);

    List<Producto> findByPrecioLessThan(double precio);
}
