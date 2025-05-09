package com.proyecto_lp2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto_lp2.model.CabeceraBoleta;
import com.proyecto_lp2.model.DetalleBoleta;
import com.proyecto_lp2.model.Order;
import com.proyecto_lp2.model.OrderDetail;
import com.proyecto_lp2.model.Producto;
import com.proyecto_lp2.repository.ICabeceraBoletaRepository;
import com.proyecto_lp2.repository.IDetalleBoletaRepository;
import com.proyecto_lp2.repository.IOrderDetailRepository;
import com.proyecto_lp2.repository.IOrderRepository;
import com.proyecto_lp2.repository.IProductoRepository;

@Service
public class VentaService {

	@Autowired
	private ICabeceraBoletaRepository cabeceraBoletaRepository;

	@Autowired
	private IDetalleBoletaRepository detalleBoletaRepository;

	@Autowired
	private IProductoRepository productoRepository;

	@Autowired
	private IOrderRepository iorder;

	@Autowired
	private IOrderDetailRepository iorderDetail;

	// Generar el número de boleta
	public String generarNumBoleta() {
		String codigo = "B0001"; // Código inicial
		Optional<CabeceraBoleta> ultimaBoleta = cabeceraBoletaRepository.findTopByOrderByNumbolDesc();

		if (ultimaBoleta.isPresent()) {
			String ultimoCodigo = ultimaBoleta.get().getFch_bol();
			int numero = Integer.parseInt(ultimoCodigo.substring(1)) + 1;
			codigo = String.format("B%04d", numero);
		}
		return codigo;
	}

	@Transactional
	public void realizarVenta(CabeceraBoleta cabecera, List<DetalleBoleta> detalles) {
		String numBoleta = generarNumBoleta();
		cabecera.setNumbol(numBoleta);
		cabeceraBoletaRepository.save(cabecera);

		for (DetalleBoleta detalle : detalles) {
			detalle.setNum_bol(numBoleta);
			detalleBoletaRepository.save(detalle);

			// Actualizar stock del producto
			Producto producto = productoRepository.findById(detalle.getIdprod())
					.orElseThrow(() -> new RuntimeException("Producto no encontrado"));
			producto.setStock(producto.getStock() - detalle.getCantidad());
			productoRepository.save(producto);
		}
	}

	@Transactional
	public void guardarOrder(Order order) {
		iorder.save(order);
	}

	@Transactional
	public void guardarOrderDetail(OrderDetail orderDetail) {
		iorderDetail.save(orderDetail);
	}

}
