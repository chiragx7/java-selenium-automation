package datafactory;

import dataobjects.UserDetails;

import java.util.Random;

public class RegistrationData {
    UserDetails userDetails = new UserDetails();

    public UserDetails registrationData() {
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(10000));
        String firstName = "Test";
        String lastName = "User";
        String randomEmail = "test-luma-"+randomNumber+"@yopmail.com";
        String password = "Test@123";
        String confirmPassword = "Test@123";

        userDetails.setFirstName(firstName);
        userDetails.setLastName(lastName);
        userDetails.setEmail(randomEmail);
        userDetails.setPassword(password);
        userDetails.setConfirmPassword(confirmPassword);
        return userDetails;
    }
}
