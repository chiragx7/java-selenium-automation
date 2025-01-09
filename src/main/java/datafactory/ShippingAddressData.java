package datafactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataobjects.AddressDetails;

import java.io.File;
import java.io.IOException;

public class ShippingAddressData {
    AddressDetails addressDetails = new AddressDetails();

    public AddressDetails addressData() {
        String firstName = "Test";
        String lastName = "User";
        String streetAddress = "JigNect Technologies";
        String city = "Ahmedabad";
        String state = "Gujarat";
        String postalCode = "380014";
        String country = "India";
        String phoneNumber = "9054766180";

        addressDetails.setFirstName(firstName);
        addressDetails.setLastName(lastName);
        addressDetails.setStreetAddress(streetAddress);
        addressDetails.setCity(city);
        addressDetails.setState(state);
        addressDetails.setPostalCode(postalCode);
        addressDetails.setCountry(country);
        addressDetails.setPhoneNumber(phoneNumber);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(new File("src/main/resources/userdata.json"));
            if (rootNode.isArray() && !rootNode.isEmpty()) {
                JsonNode firstUser = rootNode.get(0); // Get the first element of the array
                addressDetails.setEmail(firstUser.path("email").asText());
                addressDetails.setPassword(firstUser.path("password").asText());
            } else {
                throw new RuntimeException("JSON array is empty or invalid!");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file: " + e.getMessage(), e);
        }

        return addressDetails;
    }

    public AddressDetails newAddressData() {
        String firstName = "Updated";
        String lastName = "User";
        String streetAddress = "Navrangpura";
        String city = "Ahmedabad";
        String state = "Gujarat";
        String postalCode = "380009";
        String country = "India";
        String phoneNumber = "9054766180";

        addressDetails.setFirstName(firstName);
        addressDetails.setLastName(lastName);
        addressDetails.setStreetAddress(streetAddress);
        addressDetails.setCity(city);
        addressDetails.setState(state);
        addressDetails.setPostalCode(postalCode);
        addressDetails.setCountry(country);
        addressDetails.setPhoneNumber(phoneNumber);

        return addressDetails;
    }
}
