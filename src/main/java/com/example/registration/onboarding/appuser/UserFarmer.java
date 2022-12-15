package com.example.registration.onboarding.appuser;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user_farmer", uniqueConstraints = {@UniqueConstraint(columnNames = "telephone",
        name = "user_farmer_phone_unique")})
public class UserFarmer implements UserDetails {
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String userName;

    @Column(name= "telephone", nullable = false)
    private String phone;
    @Column(name= "password", nullable = false)
    private String password;
//    @Transient
//    private String confirmPassword;
    @Enumerated(EnumType.STRING)
    private Roles roles;
    private Boolean locked = false;
    private Boolean enabled = false;

    //TODO: ENSURE YOU USE THE ACCOUNT LOCKED AND ENABLED FEATURES
    //TODO: USE TWILIO TO SEND OTP TO PHONE NUMBER FOR MOBILE

    public UserFarmer(String userName, String phone, String password, Roles roles) {
        this.userName = userName;
        this.phone = phone;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(roles.name());
        return Collections.singletonList(authority);
    }


    public String getUserName() {
        return userName;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
   }