package org.example.myauthentication.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LoginResponseDTO {
    private String tokenValue;
    private Date expiryAt;
    private String email;
}
