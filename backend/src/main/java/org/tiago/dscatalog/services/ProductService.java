package org.tiago.dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tiago.dscatalog.dto.CategoryDTO;
import org.tiago.dscatalog.dto.ProductDTO;
import org.tiago.dscatalog.entities.Category;
import org.tiago.dscatalog.entities.Product;
import org.tiago.dscatalog.exceptions.DatabaseException;
import org.tiago.dscatalog.exceptions.ResourceNotFoundException;
import org.tiago.dscatalog.repositories.CategoryRepository;
import org.tiago.dscatalog.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable){
		Page<Product> pages = repository.findAll(pageable);
		return pages.map(ProductDTO::new);
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		return repository.findById(id).map(product -> new ProductDTO(product, product.getCategories()))
				.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO productDTO) {
		Product product = new Product();
		copyDtoToEntity(productDTO, product);
		product = repository.save(product);
		return new ProductDTO(product);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO productDTO) {
		try {
			Product product = repository.getReferenceById(id);
			copyDtoToEntity(productDTO, product);
			product =  repository.save(product);
			return new ProductDTO(product);
		}catch(EntityNotFoundException exception){
			throw new ResourceNotFoundException("Id not found: "+ id);
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Resource not found");
		}
		
		try {
			repository.deleteById(id);
		}catch(DataIntegrityViolationException exception) {
			throw new DatabaseException("Referential integrity violation");
		}
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product product) {
		product.setName(dto.name());
		product.setDescription(dto.description());
		product.setPrice(dto.price());
		product.setImgUrl(dto.imgUrl());
		product.setDate(dto.date());
		
		product.getCategories().clear();
		for(CategoryDTO categoryDto : dto.categories()) {
			Category category = categoryRepository.getReferenceById(categoryDto.id());
			product.getCategories().add(category);
		}
	}
}
