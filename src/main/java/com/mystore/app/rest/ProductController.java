package com.mystore.app.rest;

import com.mystore.app.entity.Product;
import com.mystore.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/create")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		Product p = productService.addProduct(product);
		return new ResponseEntity<>(p, HttpStatus.CREATED);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		if (products.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") Integer id) {
		Product p = productService.getProduct(id);
		if (p != null) {
			return new ResponseEntity<>(p, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id, @RequestBody Product product) {
		Product p = productService.updateProduct(id, product);
		if (p != null) {
			return new ResponseEntity<>(p, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
		String message = productService.deleteProduct(id);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	// API to search products by name
	@GetMapping("/getByName/{name}")
	public ResponseEntity<Product> searchProduct(@PathVariable("name") String productName) {
		Product p = productService.searchProductByName(productName);
		if (p == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	// API to filter products by category
	@GetMapping("/category/{category}")
	public ResponseEntity<List<Product>> filterProductCat(@PathVariable("category") String category) {
		List<Product> filterProduct = productService.filterProductByCategory(category);
		if (filterProduct == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(filterProduct, HttpStatus.OK);
	}

	// API to filter products by price range
	@GetMapping("/price/{minPrice}/{maxPrice}")
	public ResponseEntity<List<Product>> getFilterProductByPrice(@PathVariable("minPrice") double minPrice,
			@PathVariable("maxPrice") double maxPrice) {
		List<Product> product = productService.filterProductByPrice(minPrice, maxPrice);
		if (product == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	// API to filter products by stock quantity range
	@GetMapping("/quantity/{minStock}/{maxStock}")
	public ResponseEntity<List<Product>> getProductByQuuantityRange(@PathVariable("minStock") int minStock,
			@PathVariable("maxStock") int maxStock) {
		List<Product> filterProduct = productService.filterProductByStockQuantity(minStock, maxStock);
		if (filterProduct.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(filterProduct, HttpStatus.OK);
	}

}
