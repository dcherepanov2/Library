package com.example.MyBookShopApp.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class JwtUser implements UserDetails {

    private Long id;
    private final String name;
    private Double balance;
    private final LocalDate regTime;
    private final String hash;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(
            Long id,
            String name,
            Double balance,
            LocalDate date,
            String hash,
            Collection<? extends GrantedAuthority> authorities,
            boolean enabled
    ) {
        this.name = name;
        this.balance = balance;
        this.regTime = date;
        this.id = id;
        this.hash = hash;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }


    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Double getBalance() {
        return balance;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setBalance(Double balance){
        this.balance = balance;
    }

    public String getHash() {
        return hash;
    }

}
