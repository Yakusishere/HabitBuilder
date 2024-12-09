package com.example.habitbuilder.utils;

import com.example.habitbuilder.pojo.Manager;
import com.example.habitbuilder.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginManager implements UserDetails {

    private Manager manager;

    private User user;

    public LoginManager(Manager manager) {
        this.manager = manager;
    }

    public LoginManager(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        if (manager != null) {
            return manager.getPassword();
        }
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if (manager != null) {
            return manager.getManagerName();
        }
        return user.getUserName();
    }

    public int getUserId() {
        if (manager != null) {
            return manager.getManagerId();
        }
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
