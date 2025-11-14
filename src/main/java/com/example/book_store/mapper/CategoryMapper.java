package com.example.book_store.mapper;

import org.springframework.stereotype.Component;

import com.example.book_store.dto.CategoryDTO;
import com.example.book_store.entity.Category;
import com.example.book_store.entity.Product;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public  CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());

        if (category.getProducts() != null) {
            dto.setProductIds(
                    category.getProducts().stream()
                            .map(Product::getId)
                            .collect(Collectors.toList()));
        }

        return dto;
    }

    public Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        // Không gán productIds vào đây
        return category;
    }
}