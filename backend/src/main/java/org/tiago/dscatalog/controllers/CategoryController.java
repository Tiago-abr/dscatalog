package org.tiago.dscatalog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tiago.dscatalog.dto.CategoryDTO;
import org.tiago.dscatalog.exceptions.EntityNotFoundException;
import org.tiago.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll(){
		List<CategoryDTO> listOfAllElements = service.findAll(); 
		return ResponseEntity.ok().body(listOfAllElements);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
		return service.findById(id)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
	}
}
