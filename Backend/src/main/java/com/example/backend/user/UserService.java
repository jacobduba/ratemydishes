package com.example.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createNewUser(String netId, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        User u = new User(netId, hashedPassword);
        userRepository.save(u);
    }

    public boolean payloadisValid(LoginPayload payload) {
        if (payload.isNull()) return false;
        User user = userRepository.findByNetId(payload.netId);
        if (user == null) return false;
        String hashed = passwordEncoder.encode(payload.password);
        if (!user.getHashedPassword().equals(hashed)) return false;
        return true;
    }

//    public String jwtToken(LoginPayload payload) {
//        return
//    }

}
