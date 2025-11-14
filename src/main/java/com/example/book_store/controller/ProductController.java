// package com.example.book_store.controller;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import com.example.book_store.dto.ProductDTO;
// import com.example.book_store.service.ProductService;
// import java.util.List;
// import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
// import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// @RestController // Đánh dấu đây là REST Controller (trả dữ liệu JSON)
// @RequestMapping("/api/products") // Đường dẫn gốc cho các API sản phẩm
// public class ProductController {

//     // Tiêm ProductService để xử lý nghiệp vụ
//     private final ProductService productService;

//     // Constructor injection
//     public ProductController(ProductService productService) {
//         this.productService = productService;
//     }

//     // === TẠO SẢN PHẨM MỚI ===
//     @PostMapping // Gửi yêu cầu POST /api/products
//     public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
//         // @RequestBody: nhận dữ liệu JSON từ client và ánh xạ vào ProductDTO
//         return ResponseEntity.ok(productService.createProduct(dto));
//         // Trả về 200 OK + thông tin sản phẩm mới tạo
//     }

//     // === CẬP NHẬT SẢN PHẨM ===
//     @PutMapping("/{id}") // Gửi yêu cầu PUT /api/products/{id}
//     public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
//         // @PathVariable: lấy giá trị id trong URL
//         return ResponseEntity.ok(productService.updateProduct(id, dto));
//         // Trả về sản phẩm sau khi đã cập nhật
//     }

//     // === XÓA SẢN PHẨM ===
//     @DeleteMapping("/{id}") // Gửi yêu cầu DELETE /api/products/{id}
//     public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//         productService.deleteProduct(id); // Gọi service để xóa
//         return ResponseEntity.noContent().build(); // Trả về 204 No Content
//     }

//     // === LẤY SẢN PHẨM THEO ID ===
//     @GetMapping("/{id}") // Gửi yêu cầu GET /api/products/{id}
//     public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
//         ProductDTO dto = productService.getProductById(id);

//         // === Thêm các liên kết HATEOAS ===
//         // Giúp client biết thêm các API liên quan mà có thể thao tác
//         dto.add(linkTo(methodOn(ProductController.class).getProduct(id)).withSelfRel()); 
//         dto.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("all-products")); 
//         dto.add(linkTo(methodOn(ProductController.class).deleteProduct(id)).withRel("delete")); 
//         dto.add(linkTo(methodOn(ProductController.class).updateProduct(id, dto)).withRel("update")); 
//         return ResponseEntity.ok(dto); // Trả về 200 OK cùng dữ liệu sản phẩm + link điều hướng
//     }

//     // === LẤY TẤT CẢ SẢN PHẨM ===
//     @GetMapping // Gửi yêu cầu GET /api/products
//     public ResponseEntity<List<ProductDTO>> getAllProducts() {
//         return ResponseEntity.ok(productService.getAllProducts());
//         // Trả về danh sách tất cả sản phẩm
//     }
// }
package com.example.book_store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.book_store.dto.ProductDTO;
import com.example.book_store.service.ProductService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // === TẠO SẢN PHẨM MỚI ===
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    // === CẬP NHẬT SẢN PHẨM ===
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    // === XÓA SẢN PHẨM ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // === LẤY THEO ID ===
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        ProductDTO dto = productService.getProductById(id);
        dto.add(linkTo(methodOn(ProductController.class).getProduct(id)).withSelfRel());
        dto.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("all-products"));
        dto.add(linkTo(methodOn(ProductController.class).deleteProduct(id)).withRel("delete"));
        dto.add(linkTo(methodOn(ProductController.class).updateProduct(id, dto)).withRel("update"));
        return ResponseEntity.ok(dto);
    }

    // === LẤY TẤT CẢ ===
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // === LỌC SẢN PHẨM ===
    @GetMapping("/filter")
    public ResponseEntity<List<ProductDTO>> filterProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return ResponseEntity.ok(productService.filterProducts(categoryId, brand, minPrice, maxPrice));
    }
}
