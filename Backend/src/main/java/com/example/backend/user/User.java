package com.example.backend.user;

import com.example.backend.role.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 30, unique = true)
    private String netId;

    @Column(nullable = false)
    private String hashedPassword;

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String netId, String hashedPassword) {
        this.netId = netId;
        this.hashedPassword = hashedPassword;
    }

    public long getId() {
        return id;
    }

    public String getNetId() {
        return netId;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Set<Role> getRoles() {
        return getRoles();
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    // TODO change to more closely reflect mocked table
}
