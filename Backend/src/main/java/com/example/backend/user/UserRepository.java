package com.example.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    User findByNetId(String netId);

    void deleteByNetId(String netId);

    void deleteById(long id);
}
