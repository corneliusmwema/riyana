package com.example.registration.cart.controller;

import com.example.registration.cart.dto.cart.CartDto;
import com.example.registration.cart.dto.cart.CartResponseDto;
import com.example.registration.cart.model.Cart;
import com.example.registration.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody CartDto cartDto, @RequestParam("token") String token){
        return new ResponseEntity<>(cartService.addToCart(cartDto, token), HttpStatus.CREATED);
    }
    @GetMapping("/list")
    public ResponseEntity<CartResponseDto> getAllItemsFromCart(@RequestParam("token") String token){
        return new ResponseEntity<>(cartService.getAllItemsFromCart(token), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable String cartItemId, @RequestParam("token") String token){
        return new ResponseEntity<>(cartService.deleteCartItem(cartItemId, token), HttpStatus.NO_CONTENT);
    }
}
