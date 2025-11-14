package com.example.book_store.service;

import com.example.book_store.entity.Cart;
import com.example.book_store.entity.User;

public interface CartService {
    Cart getCartByUser(User user);
    Cart addItemToCart(User user, Long productId, int quantity);
    void removeItemFromCart(Long cartItemId);
    void clearCart(User user);
}
