package org.strawberries.userservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.strawberries.userservice.entity.User;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class AppUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Роль из вашего enum: ADMIN → "ROLE_ADMIN", USER → "ROLE_USER"
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override public String getPassword()  { return user.getPasswordHash(); }
    @Override public String getUsername()  { return user.getEmail(); }
    @Override public boolean isEnabled()   { return user.isActive(); }
    @Override public boolean isAccountNonExpired()   { return true; }
    @Override public boolean isAccountNonLocked()    { return true; }
    @Override public boolean isCredentialsNonExpired(){ return true; }

    public User getUser() { return user; }
}