// package com.example.book_store.service.impl;

// import org.springframework.stereotype.Service;

// import com.example.book_store.dto.CategoryDTO;
// import com.example.book_store.dto.ProductDTO;
// import com.example.book_store.entity.Category;
// import com.example.book_store.entity.Product;
// import com.example.book_store.exception.ResourceNotFoundException;
// import com.example.book_store.mapper.CategoryMapper;
// import com.example.book_store.mapper.ProductMapper;
// import com.example.book_store.repository.ProductRepository;
// import com.example.book_store.service.ProductService;

// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// public class ProductServiceImpl implements ProductService {

//     private final ProductRepository productRepository;
//     private final ProductMapper productMapper;
//     private CategoryDTO category;
//     private final CategoryMapper categoryMapper;

//     public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryMapper categoryMapper) {
//         this.productRepository = productRepository;
//         this.productMapper = productMapper;
//         this.categoryMapper = categoryMapper;
//     }

//     @Override
//     public ProductDTO createProduct(ProductDTO dto) {
//         Product product = productMapper.toEntity(dto);
//         return productMapper.toDTO(productRepository.save(product));
//     }

//     @Override
//     public ProductDTO updateProduct(Long id, ProductDTO dto) {
//         Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
//         product.setNameProduct(dto.getNameProduct());
//         product.setDescriptionProduct(dto.getDescriptionProduct());
//         product.setPriceProduct(dto.getPriceProduct());
//         product.setBrand(dto.getBrand());
//         product.setQuantity(dto.getQuantity());
//         product.setImageUrl(dto.getImageUrl());
//         if (dto.getCategory() != null) {
//             Category category = categoryMapper.toEntity(dto.getCategory());
//             product.setCategory(category);
//         } else {
//             product.setCategory(null); // hoặc giữ nguyên nếu muốn
//         }
//         return productMapper.toDTO(productRepository.save(product));
//     }

//     @Override
//     public void deleteProduct(Long id) {
//         productRepository.deleteById(id);
//     }

//     @Override
//     public ProductDTO getProductById(Long id) {
//         Product product = productRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID = " + id));
//         return productMapper.toDTO(product);
//     }

//     @Override
//     public List<ProductDTO> getAllProducts() {
//         return productRepository.findAll().stream().map(productMapper::toDTO).collect(Collectors.toList());
//     }
// }
package com.example.book_store.service.impl;

import org.springframework.stereotype.Service;

import com.example.book_store.dto.ProductDTO;
import com.example.book_store.entity.Product;
import com.example.book_store.exception.ResourceNotFoundException;
import com.example.book_store.mapper.ProductMapper;
import com.example.book_store.mapper.CategoryMapper;
import com.example.book_store.repository.ProductRepository;
import com.example.book_store.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper; // inject CategoryMapper

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO dto) {
        Product product = productMapper.toEntity(dto);
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID = " + id));

        product.setNameProduct(dto.getNameProduct());
        product.setDescriptionProduct(dto.getDescriptionProduct());
        product.setPriceProduct(dto.getPriceProduct());
        product.setBrand(dto.getBrand());
        product.setQuantity(dto.getQuantity());
        product.setImageUrl(dto.getImageUrl());

        // Sử dụng categoryMapper thay vì gọi productMapper.categoryDTOtoEntity()
        if (dto.getCategory() != null) {
            product.setCategory(categoryMapper.toEntity(dto.getCategory()));
        } else {
            product.setCategory(null);
        }

        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID = " + id));
        return productMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    // === Hàm lọc nâng cao ===
    @Override
    public List<ProductDTO> filterProducts(Long categoryId, String brand, Double minPrice, Double maxPrice) {
        return productRepository.findAll().stream()
                .filter(p -> (categoryId == null || (p.getCategory() != null && p.getCategory().getId().equals(categoryId))))
                .filter(p -> (brand == null || brand.isEmpty() || (p.getBrand() != null && p.getBrand().toLowerCase().contains(brand.toLowerCase()))))
                .filter(p -> (minPrice == null || p.getPriceProduct() >= minPrice))
                .filter(p -> (maxPrice == null || p.getPriceProduct() <= maxPrice))
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }
}
