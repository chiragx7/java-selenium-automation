package datafactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataobjects.UserDetails;

import java.io.File;
import java.io.IOException;

public class LoginData {
    UserDetails userDetails = new UserDetails();

    public UserDetails loginData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(new File("src/main/resources/userdata.json"));
            if (rootNode.isArray() && !rootNode.isEmpty()) {
                JsonNode firstUser = rootNode.get(0);
                UserDetails userDetails = new UserDetails();
                userDetails.setEmail(firstUser.path("email").asText());
                userDetails.setPassword(firstUser.path("password").asText());
                return userDetails;
            } else {
                throw new RuntimeException("JSON array is empty or invalid!");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file: " + e.getMessage(), e);
        }
    }

    public UserDetails invalidLoginData() {
        try {
            userDetails.setEmail("test@gmail.com");
            userDetails.setPassword("123456");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userDetails;
    }
}
