package dataobjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}

