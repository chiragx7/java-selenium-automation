package dataobjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDetails {
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phoneNumber;
    private String email;
    private String password;
}
