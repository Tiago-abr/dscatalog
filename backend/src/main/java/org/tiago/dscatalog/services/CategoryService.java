package org.tiago.dscatalog.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tiago.dscatalog.dto.CategoryDTO;
import org.tiago.dscatalog.entities.Category;
import org.tiago.dscatalog.repositories.CategoryRepository;


@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> listCategories = repository.findAll();
		
		return listCategories.stream().map(x -> new CategoryDTO(x.getId(), x.getName()))
				.collect(Collectors.toList());
	}
}
