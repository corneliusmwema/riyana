package com.example.registration.cart.model;

import com.example.registration.onboarding.appuser.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wish_list")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @JoinColumn(name = "user_id")
    private AppUser user;
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Product product;

}
