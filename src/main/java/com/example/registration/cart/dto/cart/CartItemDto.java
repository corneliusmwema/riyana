package com.example.registration.cart.dto.cart;

import com.example.registration.cart.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDto {
    private Integer id;
    private Integer quantity;
    private Product product;
}
