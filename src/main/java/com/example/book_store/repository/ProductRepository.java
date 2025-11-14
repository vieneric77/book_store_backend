// package com.example.book_store.repository;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import com.example.book_store.entity.Product;

// @Repository
// public interface ProductRepository extends JpaRepository<Product, Long> {
// }
package com.example.book_store.repository;

import com.example.book_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:brand IS NULL OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) AND " +
            "(:minPrice IS NULL OR p.priceProduct >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.priceProduct <= :maxPrice)")
    List<Product> filterProducts(
            @Param("categoryId") Long categoryId,
            @Param("brand") String brand,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );
}
