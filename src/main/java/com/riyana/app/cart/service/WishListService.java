package com.riyana.app.cart.service;

import com.riyana.app.cart.dto.ProductDto;
import com.riyana.app.cart.model.WishList;
import com.riyana.app.cart.repository.WishListRepository;
import com.riyana.app.onboarding.Entities.AppUser;
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

    public List<ProductDto> getWishListForUser(AppUser user) {
        final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();
        for(WishList wishList: wishLists){
            productDtos.add(productService.mapToDto(wishList.getProduct()));
        }
        return productDtos;
    }
}
