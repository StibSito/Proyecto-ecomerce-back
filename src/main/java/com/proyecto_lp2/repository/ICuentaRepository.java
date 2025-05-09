package com.proyecto_lp2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_lp2.model.Cuenta;

@Repository
public interface ICuentaRepository extends JpaRepository<Cuenta, Integer> {

	Cuenta findByEmailAndClave(String email, String clave);

	Cuenta findByEmail(String email);

}
