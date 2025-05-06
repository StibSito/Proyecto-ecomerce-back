package com.proyecto_lp2.listener;

import com.proyecto_lp2.service.CarritoService;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@WebListener
public class CarritoCompraListener implements HttpSessionListener {

	@Autowired
	private CarritoService carritoService;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("Inicio de sesión en la aplicación");
		carritoService.inicializarCarrito(se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
	}
}
