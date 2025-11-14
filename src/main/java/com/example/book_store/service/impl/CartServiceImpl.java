package com.example.book_store.service.impl;

import org.springframework.stereotype.Service;

import com.example.book_store.entity.Cart;
import com.example.book_store.entity.CartItem;
import com.example.book_store.entity.Product;
import com.example.book_store.entity.User;
import com.example.book_store.repository.CartItemRepository;
import com.example.book_store.repository.CartRepository;
import com.example.book_store.repository.ProductRepository;
import com.example.book_store.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    @Override
    public Cart addItemToCart(User user, Long productId, int quantity) {
        Cart cart = getCartByUser(user);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);

        cart.getItems().add(item);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public void removeItemFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void clearCart(User user) {
        Cart cart = getCartByUser(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
