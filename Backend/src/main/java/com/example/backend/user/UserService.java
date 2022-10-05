package com.example.backend.user;

import com.example.backend.user.exceptions.IncorrectUsernameOrPasswordException;
import com.example.backend.user.exceptions.InvalidPayloadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createNewUser(String netId, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User u = new User(netId, hashedPassword);
        userRepository.save(u);
    }

    public User getUser(String netId) throws NoSuchElementException {
        User user = userRepository.findByNetId(netId);
        if (user == null) throw new NoSuchElementException();
        return user;
    }

    public void validateLoginPayload(LoginPayload payload) {
        if (payload.isNull()) throw new InvalidPayloadException();
        User user = userRepository.findByNetId(payload.netId);

        if (user == null || !BCrypt.checkpw(payload.password, user.getHashedPassword())) {
            throw new IncorrectUsernameOrPasswordException();
        }
    }

//    public String jwtToken(LoginPayload payload) {
//        return
//    }

}
