package com.proyecto_lp2.controller;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto_lp2.model.Producto;
import com.proyecto_lp2.repository.ICategoriaRepository;
import com.proyecto_lp2.repository.IEstadosRepository;
import com.proyecto_lp2.repository.IProductoRepository;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
public class ProductoController {

	@Autowired
	ICategoriaRepository icate;

	@Autowired
	IProductoRepository iprod;

	@Autowired
	IEstadosRepository iest;

	@PostMapping("/addprod")
	public String addprod(@ModelAttribute Producto producto, Model model) {
		try {
			iprod.save(producto);
			model.addAttribute("lstProductos", iprod.findAll());
			model.addAttribute("lstCategorias", icate.findAll());
			model.addAttribute("lstEstados", iest.findAll());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "product-management";
	}

	@GetMapping("/edit/prod/{id}")
	public String editprod(@PathVariable String id, Model model) {
		Producto producto = iprod.findById(id).orElse(null);
		if (producto != null) {
			model.addAttribute("producto", producto);
			model.addAttribute("lstCategorias", icate.findAll());
			model.addAttribute("lstEstados", iest.findAll());
		} else {
			return "redirect:/product-management";
		}
		return "edit-product";
	}

	@PostMapping("/updateprod")
	public String updateprod(@ModelAttribute Producto producto, Model model) {
		try {
			iprod.save(producto);
			model.addAttribute("producto", new Producto());
			model.addAttribute("lstProductos", iprod.findAll());
			model.addAttribute("lstCategorias", icate.findAll());
			model.addAttribute("lstEstados", iest.findAll());
			return "product-management";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "product-management";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarprod(@PathVariable String id, Model model) {
		try {
			iprod.deleteById(id); // Eliminar producto por id
			model.addAttribute("producto", new Producto());
			model.addAttribute("lstProductos", iprod.findAll());
			model.addAttribute("lstCategorias", icate.findAll());
			model.addAttribute("lstEstados", iest.findAll());
		} catch (Exception e) {
			model.addAttribute("error", "Error al eliminar el producto con ID: " + id);
			return "product-management"; // Vuelve a la página de gestión con un mensaje de error
		}
		return "product-management"; // Redirige a la página de gestión de productos
	}

	@GetMapping("/detail/{id}")
	public String getProductDetail(@PathVariable String id, Model model) {
		// buscar ID
		Producto producto = iprod.findById(id).orElse(null);

		if (producto != null) {
			model.addAttribute("producto", producto);
			model.addAttribute("lstProductos", iprod.findAll());

			if (producto.getStock() <= 10 && producto.getStock() > 0) {
				model.addAttribute("alertaStock", true);
			} else {
				model.addAttribute("alertaStock", false);
			}

			return "product-detail";
		} else {
			return "redirect:/productos";
		}
	}

	@PostMapping("/products/filter/price/between")
	public String filterProducts(@RequestParam Double minPrice, @RequestParam Double maxPrice, Model model) {

		List<Producto> filteredProducts = iprod.findByPrecioBetween(minPrice, maxPrice);

		// Agrega la lista de productos filtrados al modelo
		model.addAttribute("lstProductos", filteredProducts);
		model.addAttribute("lstCategorias", icate.findAll());

		return "shop";
	}

	@GetMapping("/precio/fijo/100")
	public String fixedPrice100(Model model) {
		model.addAttribute("lstProductos", iprod.findByPrecioLessThan(100.00));
		model.addAttribute("lstCategorias", icate.findAll());
		return "shop";
	}

	@GetMapping("/precio/fijo/1000")
	public String fixedPrice1000(Model model) {
		model.addAttribute("lstProductos", iprod.findByPrecioLessThan(1000.00));
		model.addAttribute("lstCategorias", icate.findAll());
		return "shop";
	}

	@Autowired
	private DataSource dataSource; // javax.sql

	@Autowired
	private ResourceLoader resourceLoader; // core.io

	@GetMapping("/reportes/catalogo")
	public void reporteCatalogo(HttpServletResponse response) {
		// opción 1
		response.setHeader("Content-Disposition", "attachment; filename=\"catalogo.pdf\";");
		// opción 2
		response.setHeader("Content-Disposition", "inline;");

		response.setContentType("application/pdf");
		try {
			String ru = resourceLoader.getResource("classpath:static/catalogo.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/reportes/catalogo/filtro")
	public void reporteCatalogoCategoria(@RequestParam int id, HttpServletResponse response) {
		// opción 1
		response.setHeader("Content-Disposition", "attachment; filename=\"catalogoFiltro.pdf\";");
		// opción 2
		response.setHeader("Content-Disposition", "inline;");

		response.setContentType("application/pdf");

		try {
			// definir los parametros a enviar -? "clave":valor
			HashMap<String, Object> parametros = new HashMap<>();
			parametros.put("categoria", id);
			String ru = resourceLoader.getResource("classpath:static/catalogoFiltro.jasper").getURI().getPath();
			// llena el reporte con los parametros
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
