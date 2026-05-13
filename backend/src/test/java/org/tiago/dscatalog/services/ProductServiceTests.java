package org.tiago.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tiago.dscatalog.exceptions.ResourceNotFoundException;
import org.tiago.dscatalog.repositories.ProductRepository;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private Long validId;
	private Long invalidId;
	
	@BeforeEach
	void setUp() throws Exception {
		this.validId = 1L;
		this.invalidId = 1000L;
		
		Mockito.when(this.repository.existsById(this.validId)).thenReturn(true);
		Mockito.when(this.repository.existsById(this.invalidId)).thenReturn(false);
		Mockito.doNothing().when(this.repository).deleteById(this.validId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			this.service.delete(this.validId);
		});
		
		Mockito.verify(this.repository, Mockito.times(1)).deleteById(this.validId);
	}
	
	@Test 
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			this.service.delete(this.invalidId);
		});
		
		Mockito.verify(this.repository, Mockito.times(0)).deleteById(this.invalidId);
	}

}
