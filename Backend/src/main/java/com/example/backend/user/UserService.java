package com.example.backend.user;

import com.example.backend.user.exceptions.IncorrectUsernameOrPasswordException;
import com.example.backend.user.exceptions.InvalidPayloadException;
import com.example.backend.user.exceptions.InvalidTokenException;
import com.example.backend.user.exceptions.UserAlreadyExistsException;
import com.example.backend.user.payload.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenHelper jwtTokenHelper;

    @Autowired
    public UserService(UserRepository userRepository, JwtTokenHelper jwtTokenHelper) {
        this.userRepository = userRepository;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    public User createNewUser(String netId, String password) {
        if (userRepository.findByNetId(netId) != null) return null;
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(netId, hashedPassword);
        userRepository.save(user);
        return user;
    }

    public UserResponse getUserResponsePayload(String netId) throws NoSuchElementException {
        // This method assumes the user exists.
        return new UserResponse(userRepository.findByNetId(netId));
    }

    public User getUserFromAuthPayload(AuthRequest payload) {
        User user = userRepository.findByNetId(jwtTokenHelper.parseAccessToken(payload.getToken()));
        if (user == null) throw new InvalidTokenException();
        return user;
    }

    public void loginValidatePayload(LoginRequest payload) {
        if (payload.isNull()) throw new InvalidPayloadException();
        User user = userRepository.findByNetId(payload.getNetId());

        if (user == null || !BCrypt.checkpw(payload.getPassword(), user.getHashedPassword())) {
            throw new IncorrectUsernameOrPasswordException();
        }
    }

    public String loginGenerateJwtToken(LoginRequest payload) {
        loginValidatePayload(payload);
        User user = userRepository.findByNetId(payload.getNetId());
        return jwtTokenHelper.generateAccessToken(user);
    }

    public String registrationGenerateJwtToken(RegisterRequest registerRequest) {
        User user = userRepository.findByNetId(registerRequest.getNetId());
        return jwtTokenHelper.generateAccessToken(user);
    }

    // This method checks to make sure that user does not exist, password is valid.
    public User registerUser(RegisterRequest registerRequest) {
        User user = userRepository.findByNetId(registerRequest.getNetId());
        if (user != null) throw new UserAlreadyExistsException();
        return createNewUser(registerRequest.getNetId(), registerRequest.getPassword());
    }

    public User deleteUser(DeleteRequest deleteRequest) {
        User user = getUserFromAuthPayload(deleteRequest);
        if (!BCrypt.checkpw(deleteRequest.getPassword(), user.getHashedPassword())) throw new IncorrectUsernameOrPasswordException();

        userRepository.deleteById(user.getId());

        return user;
    }

    // TODO refactor to not use these payload objects... code quality suffering
    public void changeUserPW(ChangePasswordRequest changePasswordRequestPayload) {
        User user = getUserFromAuthPayload(changePasswordRequestPayload);

        if (!BCrypt.checkpw(changePasswordRequestPayload.getOldPassword(), user.getHashedPassword())) {
            throw new IncorrectUsernameOrPasswordException();
        }

        user.setHashedPassword(BCrypt.hashpw(changePasswordRequestPayload.getNewPassword(), BCrypt.gensalt()));

        userRepository.save(user);
    }
}
