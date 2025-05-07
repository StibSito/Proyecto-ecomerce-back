package com.proyecto_lp2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_lp2.model.OrderDetail;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

	List<OrderDetail> findByObjOrderIdorder(int idorder);
}
