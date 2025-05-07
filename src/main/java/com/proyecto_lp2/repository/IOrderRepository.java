package com.proyecto_lp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_lp2.model.Order;
import java.util.List;


@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findByIdcuenta(int idcuenta);
}
