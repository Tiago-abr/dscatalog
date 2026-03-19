package org.tiago.dscatalog.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tiago.dscatalog.dto.CategoryDTO;
import org.tiago.dscatalog.entities.Category;
import org.tiago.dscatalog.exceptions.ResourceNotFoundException;
import org.tiago.dscatalog.repositories.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> listCategories = repository.findAll();
		
		return listCategories.stream().map(CategoryDTO::new)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Optional<CategoryDTO> findById(Long id) {
		return repository.findById(id).map(CategoryDTO::new);
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
}
