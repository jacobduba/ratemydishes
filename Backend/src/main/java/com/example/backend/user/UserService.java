package com.example.backend.user;

import com.example.backend.user.exceptions.IncorrectUsernameOrPasswordException;
import com.example.backend.user.exceptions.InvalidPayloadException;
import com.example.backend.user.exceptions.UserAlreadyExistsException;
import com.example.backend.user.payload.AuthRequestPayload;
import com.example.backend.user.payload.LoginRequestPayload;
import com.example.backend.user.payload.RegisterRequestPayload;
import com.example.backend.user.payload.UserResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private UserRepository userRepository;
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    public UserService(UserRepository userRepository, JwtTokenHelper jwtTokenHelper) {
        this.userRepository = userRepository;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    public User createNewUser(String netId, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(netId, hashedPassword);
        userRepository.save(user);
        return user;
    }

    public UserResponsePayload getUserResponsePayload(String netId) throws NoSuchElementException {
        // This method assumes the user exists.
        return new UserResponsePayload(userRepository.findByNetId(netId));
    }

    public User getUserFromAuthPayload(AuthRequestPayload payload) {
        return userRepository.findByNetId(jwtTokenHelper.parseAccessToken(payload.getToken()));
    }

    public void validateLoginPayload(LoginRequestPayload payload) {
        if (payload.isNull()) throw new InvalidPayloadException();
        User user = userRepository.findByNetId(payload.getNetId());

        if (user == null || !BCrypt.checkpw(payload.getPassword(), user.getHashedPassword())) {
            throw new IncorrectUsernameOrPasswordException();
        }
    }

    public String generateJwtToken(LoginRequestPayload payload) {
        validateLoginPayload(payload);
        User user = userRepository.findByNetId(payload.getNetId());
        return jwtTokenHelper.generateAccessToken(user);
   }

    /**
     * This method checks to make sure that user does not exist, password is valid.
     * @param registerRequestPayload
     * @return
     */
    public User registerUser(RegisterRequestPayload registerRequestPayload) {
        User user = userRepository.findByNetId(registerRequestPayload.getNetId());
        if (user != null) throw new UserAlreadyExistsException();
        return createNewUser(registerRequestPayload.getNetId(), registerRequestPayload.getPassword());
    }
}
