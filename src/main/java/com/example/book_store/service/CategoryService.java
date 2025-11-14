package com.example.book_store.service;

import java.util.List;

import com.example.book_store.dto.CategoryDTO;

public interface CategoryService {
    CategoryDTO create(CategoryDTO dto);
    CategoryDTO update(Long id, CategoryDTO dto);
    void delete(Long id);
    CategoryDTO getById(Long id);
    List<CategoryDTO> getAll();
}

