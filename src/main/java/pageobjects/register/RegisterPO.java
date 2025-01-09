package pageobjects.register;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dataobjects.UserDetails;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

import java.io.File;
import java.io.IOException;

public class RegisterPO extends BasePO {
    public RegisterPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='panel header']/ul/li[@class='authorization-link']/following-sibling::li/a")
    private WebElement createAnAccountLink;

    @FindBy(id = "firstname")
    private WebElement firstNameField;

    @FindBy(id = "lastname")
    private WebElement lastNameField;

    @FindBy(id = "email_address")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "password-confirmation")
    private WebElement confirmPasswordField;

    @FindBy(xpath = "//button[@type='submit' and @title='Create an Account']")
    private WebElement createAnAccountButton;

    @FindBy(xpath = "//div[contains(@class,'message-success')]/div")
    private WebElement successMessage;

    @FindBy(xpath = "//div[contains(@class,'message-error')]/div")
    private WebElement errorMessageLabel;

    @FindBy(xpath = "//div[@class='panel header']/ul/li/span[@class='logged-in']")
    private WebElement userNameLabel;

    public String getErrorMessage() {
        return selenium.getText(errorMessageLabel);
    }

    public String getActualSuccessMessage() {
        return selenium.getText(successMessage);
    }

    public void clickCreateAnAccountLink() {
        createAnAccountLink.click();
    }

    public void registerWithRequiredDetails(UserDetails userDetails) {
        enterFirstName(userDetails.getFirstName());
        enterLastName(userDetails.getLastName());
        enterEmail(userDetails.getEmail());
        enterPassword(userDetails.getPassword());
        enterConfirmPassword(userDetails.getConfirmPassword());
        clickOnCreateAnAccountButton();
    }

    public void enterFirstName(String firstName) {
        selenium.type(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        selenium.type(lastNameField, lastName);
    }

    public void enterEmail(String email) {
        selenium.type(emailField, email);
    }

    public void enterPassword(String password) {
        selenium.type(passwordField, password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        selenium.type(confirmPasswordField, confirmPassword);
    }

    public void clickOnCreateAnAccountButton() {
        selenium.click(createAnAccountButton);
    }

    public void storeValidUsers(UserDetails userDetails) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/main/resources/userdata.json");

            ArrayNode usersArray = objectMapper.createArrayNode();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("email", userDetails.getEmail());
            objectNode.put("password", userDetails.getPassword());
            usersArray.add(objectNode);

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, usersArray);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
