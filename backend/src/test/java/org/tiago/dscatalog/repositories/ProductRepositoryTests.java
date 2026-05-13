package org.tiago.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.tiago.dscatalog.entities.Product;
import org.tiago.dscatalog.factory.ProductFactory;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private Long validId;
	private Long invalidId;
	private Long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		this.validId = 1L;
		this.invalidId = 1000L;
		this.countTotalProducts = 25L;
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		this.repository.deleteById(this.validId);
		Optional<Product> result = this.repository.findById(this.validId);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdDoesNotExist() {
	    this.repository.deleteById(this.invalidId);
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		Product product = ProductFactory.createProduct();
		product.setId(null);
		
		product = this.repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(this.countTotalProducts + 1, product.getId());
	}
	
	@Test
	public void findByIdShouldReturnProductWhenIdExists() {
		Optional<Product> product = this.repository.findById(this.validId);
		
		Assertions.assertTrue(product.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
		Optional<Product> product = this.repository.findById(this.invalidId);
		
		Assertions.assertTrue(product.isEmpty());
	}
}
