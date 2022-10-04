package com.example.backend.user;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User implements UserDetails { // Implementing user details allows us to plug into spring-security which handles some authentication for us
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 30, unique = true)
    private String netId;

    @Column(nullable = false, length = 64)
    private String password;

    public User() {}

    public User(String netId, String password) {
        this.netId = netId;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getNetId() {
        return netId;
    }

    /*
    UserDetails methods
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
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

    // TODO change to more closely reflect mocked table
}
