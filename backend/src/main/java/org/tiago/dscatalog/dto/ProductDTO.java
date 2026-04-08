package org.tiago.dscatalog.dto;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.tiago.dscatalog.entities.Category;
import org.tiago.dscatalog.entities.Product;

public record ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date, List<CategoryDTO> categories) {
	
	public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		this(id, name, description, price, imgUrl, date, Collections.emptyList());
	}
	
	public ProductDTO(Product product) {
		this(product, Collections.emptySet());
	}
	
	public ProductDTO(Product product, Set<Category> categories) {
		this(product.getId(),
			product.getName(),
			product.getDescription(),
			product.getPrice(),
			product.getImgUrl(),
			product.getDate(),
			categories.stream().map(CategoryDTO::new).toList());
	}
}