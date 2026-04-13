package org.tiago.dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tiago.dscatalog.dto.CategoryDTO;
import org.tiago.dscatalog.entities.Category;
import org.tiago.dscatalog.exceptions.DatabaseException;
import org.tiago.dscatalog.exceptions.ResourceNotFoundException;
import org.tiago.dscatalog.repositories.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable){
		Page<Category> pages = repository.findAll(pageable);
		return pages.map(CategoryDTO::new);
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		return repository.findById(id).map(CategoryDTO::new).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
	}
	
	@Transactional
	public CategoryDTO insert(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.name());
		category = repository.save(category);
		return new CategoryDTO(category);
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
		try {
			Category category = repository.getReferenceById(id);
			category.setName(categoryDTO.name());
			category = repository.save(category);
			return new CategoryDTO(category);
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
			this.repository.deleteById(id);
		}catch(DataIntegrityViolationException exception) {
			throw new DatabaseException("Referential integrity violation");
		}
	}
}
