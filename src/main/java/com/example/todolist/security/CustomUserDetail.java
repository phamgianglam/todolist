package com.example.todolist.security;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.todolist.model.Permission;
import com.example.todolist.model.Profile;

public class CustomUserDetail implements UserDetails {
    private final Profile profile;

    public CustomUserDetail(Profile profile) {
        this.profile = profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return profile.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()) ).toList();

    }

    @Override
    public String getPassword() {
        return profile.getPassword();
    }

    @Override
    public String getUsername() {
        return profile.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return  !profile.isPasswordNonExpired() ||
                profile.getLastPasswordResetDate().plusDays(profile.getDaysToExpirePassword()).isAfter(ZonedDateTime.now()) ;
    }

    @Override
    public boolean isAccountNonLocked() {
        return profile.isAccountNonBlocked();
    }
}
