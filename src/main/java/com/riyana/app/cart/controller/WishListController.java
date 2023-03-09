package com.riyana.app.cart.controller;

import com.riyana.app.cart.dto.ProductDto;
import com.riyana.app.cart.model.Product;
import com.riyana.app.cart.model.WishList;
import com.riyana.app.cart.service.AuthenticationService;
import com.riyana.app.cart.service.WishListService;
import com.riyana.app.onboarding.Entities.AppUser;
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
        AppUser user = authenticationService.getUser(token);
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
        AppUser user = authenticationService.getUser(token);
        return new ResponseEntity<>(wishListService.getWishListForUser(user), HttpStatus.OK);
    }
}
