package com.proyecto_lp2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto_lp2.model.Cuenta;
import com.proyecto_lp2.repository.IOrderDetailRepository;
import com.proyecto_lp2.repository.IOrderRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private IOrderRepository iorder;

	@Autowired
	private IOrderDetailRepository iorderDetail;

	@GetMapping("/history")
	public String orderHistory(Model model, HttpSession session) {
		Cuenta cuenta = (Cuenta) session.getAttribute("cuentaLogeada");
		model.addAttribute("lstOrders", iorder.findByIdcuenta(cuenta.getIdcuenta()));
		model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		return "order-history";
	}

	@GetMapping("/order-detail")
	public String orderDetail(@RequestParam("order-id") int orderId, Model model, HttpSession session) {
		model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		model.addAttribute("lstOrderDetail", iorderDetail.findByObjOrderIdorder(orderId));
		model.addAttribute("order", iorder.findById(orderId).orElse(null));
		return "order-detail";
	}

	@GetMapping("/control")
	public String orderManagment(Model model, HttpSession session) {
		model.addAttribute("cantArticulos", session.getAttribute("cantArticulos"));
		model.addAttribute("lstOrderDetail", iorderDetail.findAll());
		model.addAttribute("order", iorder.findAll());
		return "order-management";

	}

}
