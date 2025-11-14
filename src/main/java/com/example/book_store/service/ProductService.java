// package com.example.book_store.service;

// import java.util.List;

// import com.example.book_store.dto.ProductDTO;

// public interface ProductService {
//     ProductDTO createProduct(ProductDTO dto);
//     ProductDTO updateProduct(Long id, ProductDTO dto);
//     void deleteProduct(Long id);
//     ProductDTO getProductById(Long id);
//     List<ProductDTO> getAllProducts();
// }
package com.example.book_store.service;

import java.util.List;
import com.example.book_store.dto.ProductDTO;

public interface ProductService {
    ProductDTO createProduct(ProductDTO dto);
    ProductDTO updateProduct(Long id, ProductDTO dto);
    void deleteProduct(Long id);
    ProductDTO getProductById(Long id);
    List<ProductDTO> getAllProducts();

    // Bộ lọc nâng cao
    List<ProductDTO> filterProducts(Long categoryId, String brand, Double minPrice, Double maxPrice);
}
