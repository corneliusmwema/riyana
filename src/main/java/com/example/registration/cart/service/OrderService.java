package com.example.registration.cart.service;

import com.example.registration.cart.dto.checkout.CheckoutItemDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final AuthenticationService authenticationService;
    @Value("sk_test_51KzLpzFB4AxkeetMHcZTVhLT4fqMLj5xcFmmDwH4XPJQ3WjrB4idIYauN9tigQcqIphAgTJerbmIp9DOUBDuACCt00nnJtRnuv")
    private String apiKey;
    @Value("https://shamba-app-onboarding.herokuapp.com/")
    private String baseUrl;
    public Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        String successUrl = baseUrl + "payment/success";
        String failureUrl = baseUrl + "payment/failed";
        Stripe.apiKey = apiKey;
        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();
        for(CheckoutItemDto checkoutItemDto: checkoutItemDtoList)
            sessionItemList.add(createSessionLineItem(checkoutItemDto));
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureUrl)
                .setSuccessUrl(successUrl)
                .addAllLineItem(sessionItemList)
                .build();
        return Session.create(params);
    }

    private SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem
                .builder()
                .setPriceData(createPriceData(checkoutItemDto))
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData
                .builder()
                .setCurrency("usd")
                .setUnitAmount((long)(checkoutItemDto.getPrice().longValue()*100))
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getProductName())
                                .build()
                )
                .build();
    }
}
