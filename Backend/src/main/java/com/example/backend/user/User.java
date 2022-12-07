package com.example.backend.user;

import com.example.backend.role.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 30, unique = true)
    private String netId;

    @Column(nullable = false)
    private String hashedPassword;

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String netId, String hashedPassword) {
        this.netId = netId;
        this.hashedPassword = hashedPassword;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public boolean hasRole(String role) {
        // Contains does not use equals for sets, apparently
        for (Role r : this.roles) {
            if (r.getName().equals(role)) {
                return true;
            }
        }
        return false;
    }

    // TODO change to more closely reflect mocked table
}
