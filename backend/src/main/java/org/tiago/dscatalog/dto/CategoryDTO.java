package org.tiago.dscatalog.dto;

import org.tiago.dscatalog.entities.Category;

public record CategoryDTO(Long id, String name) {
	
	public CategoryDTO(Category category) {
		this(category.getId(), category.getName());
	}
}
