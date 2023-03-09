package com.riyana.app.cart.dto.cart;

import com.riyana.app.cart.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDto {
    private Integer id;
    private Integer quantity;
    private Product product;
}
