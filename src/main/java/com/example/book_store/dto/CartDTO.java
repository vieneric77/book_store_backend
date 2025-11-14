package com.example.book_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id; // Dữ liệu nội bộ, không muốn gửi ra ngoài
    private List<CartItemDTO> items;
    private double totalPrice;
}