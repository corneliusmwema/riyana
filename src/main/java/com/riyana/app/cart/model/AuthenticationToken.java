package com.riyana.app.cart.model;

import com.riyana.app.onboarding.Entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private Date createdDate;
    @OneToOne(targetEntity = AppUser.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser user;

    public AuthenticationToken(AppUser user) {
        this.user = user;
        this.createdDate = new Date();
        this.token = UUID.randomUUID().toString();

    }
}
