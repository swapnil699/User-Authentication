package org.example.myauthentication.controllers;

import org.example.myauthentication.dtos.*;
import org.example.myauthentication.dtos.ResponseStatus;
import org.example.myauthentication.models.Token;
import org.example.myauthentication.models.User;
import org.example.myauthentication.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // localhost:8080/users/...
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    Take the requests from the users
    1. signup
    2. login
    3. logout
    4. validate token
     */
    @PostMapping("/signup")
    public SignupResponseDTO signup(@RequestBody SignupRequestDTO signupRequestDTO) { //REST method
        String name = signupRequestDTO.getName();
        String email = signupRequestDTO.getEmail();
        String password = signupRequestDTO.getPassword();

        //call the user service to register the user and return the user back to the client
        //return user response dto back to the client

        SignupResponseDTO signupResponseDTO = new SignupResponseDTO();
        try {
            User user = userService.signup(name, email, password);
            signupResponseDTO.setEmail(user.getEmail());
            signupResponseDTO.setName(user.getName());
            signupResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            signupResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }

        return signupResponseDTO;
    }
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO){
        Token token =  userService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setEmail(token.getUser().getEmail());
        loginResponseDTO.setTokenValue(token.getValue());
        loginResponseDTO.setExpiryAt(token.getExpiryAt());

        return loginResponseDTO;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDTO logoutRequestDTO){
        userService.logout(logoutRequestDTO.getToken());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}") //users/validate/djwsbfhjed
    public UserDTO validateToken(@PathVariable("token") @NonNull String token){
        return UserDTO.from(userService.validateToken(token));
    }
}
