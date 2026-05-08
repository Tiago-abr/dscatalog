package org.tiago.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.tiago.dscatalog.entities.Product;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private Long validId;
	private Long invalidId;
	
	@BeforeEach
	void setUp() throws Exception {
		this.validId = 1L;
		this.invalidId = 1000L;
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		this.repository.deleteById(this.validId);
		Optional<Product> result = this.repository.findById(this.validId);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdDoesNotExist() {
	    repository.deleteById(this.invalidId);
	}
}
