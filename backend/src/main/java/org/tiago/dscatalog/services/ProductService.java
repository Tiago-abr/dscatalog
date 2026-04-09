package org.tiago.dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tiago.dscatalog.dto.ProductDTO;
import org.tiago.dscatalog.entities.Product;
import org.tiago.dscatalog.exceptions.ResourceNotFoundException;
import org.tiago.dscatalog.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		Page<Product> pages = repository.findAll(pageRequest);
		return pages.map(ProductDTO::new);
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		return repository.findById(id).map(product -> new ProductDTO(product, product.getCategories()))
				.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
	}
}
