package org.tiago.dscatalog.factory;

import java.time.Instant;

import org.tiago.dscatalog.dto.ProductDTO;
import org.tiago.dscatalog.entities.Category;
import org.tiago.dscatalog.entities.Product;

public class ProductFactory {
	
	public static Product createProduct() {
		Product product = new Product(26L, "Mouse pad", "Dê um upgrade na sua precisão!", 30.00, "img.png", Instant.now());
		product.getCategories().add(new Category(4L, "Periféricos"));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
}
