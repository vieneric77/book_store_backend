package com.example.book_store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.book_store.entity.Cart;
import com.example.book_store.entity.CartItem;
import com.example.book_store.entity.User;
import com.example.book_store.repository.UserRepository;
import com.example.book_store.service.CartService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    // Lấy giỏ hàng hiện tại (nếu đăng nhập)
    @GetMapping
    public ResponseEntity<?> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        Cart cart = cartService.getCartByUser(user);

        // Chuyển CartItem thành "flat object" chỉ chứa các thông tin cần thiết
        List<Object> flatItems = cart.getItems().stream().map(item -> {
            return new Object() {
                public final Long id = item.getId();
                public final Long productId = item.getProduct().getId();
                public final String name = item.getProduct().getNameProduct();
                public final double price = item.getProduct().getPriceProduct();
                public final int quantity = item.getQuantity();
                public final String imageUrl = item.getProduct().getImageUrl();
            };
        }).collect(Collectors.toList());

        return ResponseEntity.ok(new Object() {
            public final Long id = cart.getId();
            public final List<Object> items = flatItems;
        });
    }

    // Thêm sản phẩm vào giỏ
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        Cart updatedCart = cartService.addItemToCart(user, productId, quantity);

        // flatten giống như getCart
        List<Object> flatItems = updatedCart.getItems().stream().map(item -> {
            return new Object() {
                public final Long id = item.getId();
                public final Long productId = item.getProduct().getId();
                public final String name = item.getProduct().getNameProduct();
                public final double price = item.getProduct().getPriceProduct();
                public final int quantity = item.getQuantity();
                public final String imageUrl = item.getProduct().getImageUrl();
            };
        }).collect(Collectors.toList());

        return ResponseEntity.ok(new Object() {
            public final Long id = updatedCart.getId();
            public final List<Object> items = flatItems;
        });
    }

    // Xóa sản phẩm khỏi giỏ
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

    // Xóa toàn bộ giỏ hàng
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        cartService.clearCart(user);
        return ResponseEntity.noContent().build();
    }
}
