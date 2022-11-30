package com.example.backend.role;

import com.example.backend.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @Column
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public boolean equals(Role role) {
        return true;
    }
}
