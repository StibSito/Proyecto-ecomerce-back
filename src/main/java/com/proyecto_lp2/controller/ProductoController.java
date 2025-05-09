package com.proyecto_lp2.controller;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto_lp2.model.Producto;
import com.proyecto_lp2.repository.IProductoRepository;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping("/api/productos") 
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

	@Autowired
	private IProductoRepository iprod;
	
	 @GetMapping
	    public ResponseEntity<Map<String, Object>> mostrarGestionProductos(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size) {

	        Pageable pageable = PageRequest.of(page, size);
	        Page<Producto> productosPage = iprod.findAll(pageable);

	        Map<String, Object> response = new HashMap<>();
	        response.put("productos", productosPage.getContent());
	        response.put("currentPage", productosPage.getNumber());
	        response.put("totalItems", productosPage.getTotalElements());
	        response.put("totalPages", productosPage.getTotalPages());

	        return ResponseEntity.ok(response);
	    }

	@PostMapping
	public ResponseEntity<Producto> addprod(@RequestBody Producto producto) {
		try {
			Producto savedProducto = iprod.save(producto);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedProducto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Producto> updateprod(@PathVariable String id, @RequestBody Producto producto) {
		if (!iprod.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
		}

		producto.setIdprod(id); 
		Producto updatedProducto = iprod.save(producto);
		return ResponseEntity.ok(updatedProducto); 
	}

	private boolean deleteProductById(String id) {
		if (!iprod.existsById(id)) {
			return false;
		}
		iprod.deleteById(id);
		return true;
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> eliminarprod(@PathVariable String id) {
		boolean wasDeleted = deleteProductById(id);

		Map<String, Boolean> result = new HashMap<>();
		result.put("deleted", wasDeleted);

		if (wasDeleted) {
			return ResponseEntity.ok(result); 
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);

		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Producto> getProductDetail(@PathVariable String id) {
		Producto producto = iprod.findById(id).orElse(null);
		if (producto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
		}

		return ResponseEntity.ok(producto); 
	}

	@GetMapping("/filter/price/between")
	public ResponseEntity<List<Producto>> filterProducts(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
		List<Producto> filteredProducts = iprod.findByPrecioBetween(minPrice, maxPrice);
		return ResponseEntity.ok(filteredProducts); 
	}


	@GetMapping("/precio/fijo/100")
	public ResponseEntity<List<Producto>> fixedPrice100() {
		List<Producto> productos = iprod.findByPrecioLessThan(100.00);
		return ResponseEntity.ok(productos); 
	}

	@GetMapping("/precio/fijo/1000")
	public ResponseEntity<List<Producto>> fixedPrice1000() {
		List<Producto> productos = iprod.findByPrecioLessThan(1000.00);
		return ResponseEntity.ok(productos);
	}

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ResourceLoader resourceLoader;

	@GetMapping("/reportes/catalogo")
	public void reporteCatalogo(HttpServletResponse response) {

		response.setHeader("Content-Disposition", "attachment; filename=\"catalogo.pdf\";");

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

		response.setHeader("Content-Disposition", "attachment; filename=\"catalogoFiltro.pdf\";");

		response.setHeader("Content-Disposition", "inline;");

		response.setContentType("application/pdf");

		try {

			HashMap<String, Object> parametros = new HashMap<>();
			parametros.put("categoria", id);
			String ru = resourceLoader.getResource("classpath:static/catalogoFiltro.jasper").getURI().getPath();

			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
