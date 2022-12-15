package com.example.registration.cart.dto.cart;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class CartDto {
    private Integer id;
    private @NotNull Integer productId;
    private @NotNull Integer quantity;
}
