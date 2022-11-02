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
    private UserRepository userRepository;
    private JwtTokenHelper jwtTokenHelper;

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

    public UserResponsePayload getUserResponsePayload(String netId) throws NoSuchElementException {
        // This method assumes the user exists.
        return new UserResponsePayload(userRepository.findByNetId(netId));
    }

    public User getUserFromAuthPayload(AuthRequestPayload payload) {
        User user = userRepository.findByNetId(jwtTokenHelper.parseAccessToken(payload.getToken()));
        if (user == null) throw new InvalidTokenException();
        return user;
    }

    public void loginValidatePayload(LoginRequestPayload payload) {
        if (payload.isNull()) throw new InvalidPayloadException();
        User user = userRepository.findByNetId(payload.getNetId());

        if (user == null || !BCrypt.checkpw(payload.getPassword(), user.getHashedPassword())) {
            throw new IncorrectUsernameOrPasswordException();
        }
    }

    public String loginGenerateJwtToken(LoginRequestPayload payload) {
        loginValidatePayload(payload);
        User user = userRepository.findByNetId(payload.getNetId());
        return jwtTokenHelper.generateAccessToken(user);
    }

    public String registrationGenerateJwtToken(RegisterRequestPayload registerRequestPayload) {
        User user = userRepository.findByNetId(registerRequestPayload.getNetId());
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

    public String deleteUser(DeleteRequestPayload deleteRequestPayload) {
        User user = getUserFromAuthPayload(deleteRequestPayload);
        if (!BCrypt.checkpw(deleteRequestPayload.getPassword(), user.getHashedPassword())) throw new IncorrectUsernameOrPasswordException();

        userRepository.deleteById(user.getId());

        return user.getNetId();
    }

    // TODO refactor to not use these payload objects... code quality suffering
    public void changeUserPW(ChangePasswordPayload changePasswordPayload) {
        User user = getUserFromAuthPayload(changePasswordPayload);

        if (!BCrypt.checkpw(changePasswordPayload.getOldPassword(), user.getHashedPassword())) {
            throw new IncorrectUsernameOrPasswordException();
        }

        user.setHashedPassword(BCrypt.hashpw(changePasswordPayload.getNewPassword(), BCrypt.gensalt()));

        userRepository.save(user);
    }
}
