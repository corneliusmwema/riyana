package com.example.registration.cart.service;

import com.example.registration.cart.dto.ProductDto;
import com.example.registration.cart.model.WishList;
import com.example.registration.cart.repository.WishListRepository;
import com.example.registration.onboarding.appuser.UserFarmer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final ProductService productService;
    public WishList createWishList(WishList wishList) {
        return wishListRepository.save(wishList);
    }

    public List<ProductDto> getWishListForUser(UserFarmer user) {
        final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();
        for(WishList wishList: wishLists){
            productDtos.add(productService.mapToDto(wishList.getProduct()));
        }
        return productDtos;
    }
}
