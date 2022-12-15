package com.example.registration.cart.controller;

import com.example.registration.cart.dto.ProductDto;
import com.example.registration.cart.model.Product;
import com.example.registration.cart.model.WishList;
import com.example.registration.cart.service.AuthenticationService;
import com.example.registration.cart.service.WishListService;
import com.example.registration.onboarding.appuser.UserFarmer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/wish-list")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;
    private final AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<WishList> addToWishList(@RequestBody Product product, @RequestParam("token") String token){
        authenticationService.authenticate(token);
        UserFarmer user = authenticationService.getUser(token);
        WishList wishList = new WishList();
        wishList.setUser(user);
        wishList.setProduct(product);
        wishList.setCreatedDate(new Date());
        System.out.println(wishList);
        return new ResponseEntity<>(wishListService.createWishList(wishList), HttpStatus.CREATED);
    }
    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable String token){
        authenticationService.authenticate(token);
        UserFarmer user = authenticationService.getUser(token);
        return new ResponseEntity<>(wishListService.getWishListForUser(user), HttpStatus.OK);
    }
}
