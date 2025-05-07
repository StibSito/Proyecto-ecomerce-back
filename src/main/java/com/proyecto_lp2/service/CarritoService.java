package com.proyecto_lp2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto_lp2.model.Carrito;
import com.proyecto_lp2.model.DetalleBoleta;
import com.proyecto_lp2.repository.ICarritoRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CarritoService {

	@Autowired
	private ICarritoRepository carritoRepository;

	public void inicializarCarrito(HttpSession session) {
		if (session.getAttribute("carro") == null) {
			session.setAttribute("carro", new ArrayList<DetalleBoleta>());
			session.setAttribute("subTotalVenta", 0.00);
			session.setAttribute("cantArticulos", 0);
		}
	}


	@SuppressWarnings("unchecked")
	public List<DetalleBoleta> obtenerCarrito(HttpSession session) {
		return (List<DetalleBoleta>) session.getAttribute("carro");
	}

	public List<Carrito> findByIdcuenta(int idcuenta) {
		return carritoRepository.findByIdcuenta(idcuenta);
	}

	public Carrito findByIdcuentaAndIdprod(int idcuenta, String idprod) {
		return carritoRepository.findByIdcuentaAndIdprod(idcuenta, idprod);
	}

	public void save(Carrito carrito) {
		carritoRepository.save(carrito);
	}

	public void eliminarProducto(int idcuenta, String idprod) {
		Carrito carrito = carritoRepository.findByIdcuentaAndIdprod(idcuenta, idprod);
		if (carrito != null) {
			carritoRepository.delete(carrito);
		}
	}

	public void actualizarCantidadProducto(int idcuenta, String idprod, int nuevaCantidad) {
		carritoRepository.actualizarCantidadProducto(idcuenta, idprod, nuevaCantidad);
	}
}
