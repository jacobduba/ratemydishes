package com.example.backend.user;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 30, unique = true)
    private String netId;

    @Column(nullable = false)
    private String hashedPassword;

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

    // TODO change to more closely reflect mocked table
}
