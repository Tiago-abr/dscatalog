package org.tiago.dscatalog.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.tiago.dscatalog.dto.CategoryDTO;
import org.tiago.dscatalog.exceptions.ResourceNotFoundException;
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
				.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody CategoryDTO categoryDTO){
		categoryDTO = service.insert(categoryDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(categoryDTO.id()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id){
		categoryDTO = service.update(id, categoryDTO);
		return ResponseEntity.ok(categoryDTO);
	}
}
