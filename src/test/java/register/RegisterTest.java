package register;

import base.BaseTest;
import datafactory.RegistrationData;
import dataobjects.UserDetails;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.base.BasePO;
import pageobjects.register.RegisterPO;
import utils.Constants;

public class RegisterTest extends BaseTest {
    BasePO basePO;
    RegisterPO registerPO;
    UserDetails registrationData;
    String actualMessage;
    String expectedMessage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        basePO = new BasePO(driver);
        registerPO = new RegisterPO(driver);
        registrationData = new RegistrationData().registrationData();

        Reporter.log("Navigate to Home Page", true);
        basePO.navigateToHomePage();
        Assert.assertTrue(basePO.isPageTitleDisplayed("Home Page"), "Home Page title does not match!");

        Reporter.log("Click on Create an Account link", true);
        registerPO.clickCreateAnAccountLink();
        Assert.assertTrue(basePO.isPageTitleDisplayed("Create New Customer Account"), "Register Page title does not match!");
    }

    @Test(priority = 1,
            description = "Verify that validation message is displayed when all the fields are blank.")
    public void verifyThatValidationMessageIsDisplayedWhenAllTheFieldsAreBlank() {
        expectedMessage = "This is a required field.";

        Reporter.log("Step 1: Click on the Create an Account button", true);
        registerPO.clickOnCreateAnAccountButton();
        actualMessage = basePO.getValidationMessageByFieldName("First Name");
        Assert.assertEquals(actualMessage, expectedMessage, "Empty field validation message is not displayed for the First Name field!");

        Reporter.log("Step 2: Enter data in First Name field, click on the Create an Account button", true);
        registerPO.enterFirstName(registrationData.getFirstName());
        registerPO.clickOnCreateAnAccountButton();
        actualMessage = basePO.getValidationMessageByFieldName("Last Name");
        Assert.assertEquals(actualMessage, expectedMessage, "Empty field validation message is not displayed for the Last Name field!");

        Reporter.log("Step 3: Enter data in Last Name field, click on the Create an Account button", true);
        registerPO.enterLastName(registrationData.getLastName());
        registerPO.clickOnCreateAnAccountButton();
        actualMessage = basePO.getValidationMessageByFieldName("Email");
        Assert.assertEquals(actualMessage, expectedMessage, "Empty field validation message is not displayed for the Email field!");

        Reporter.log("Step 4: Enter data in Email field, click on the Create an Account button", true);
        registerPO.enterEmail(registrationData.getEmail());
        registerPO.clickOnCreateAnAccountButton();
        actualMessage = basePO.getValidationMessageByFieldName("Password");
        Assert.assertEquals(actualMessage, expectedMessage, "Empty field validation message is not displayed for the Password field!");

        Reporter.log("Step 5: Enter data in Password field, click on the Create an Account button", true);
        registerPO.enterPassword(registrationData.getPassword());
        registerPO.clickOnCreateAnAccountButton();
        actualMessage = basePO.getValidationMessageByFieldName("Confirm Password");
        Assert.assertEquals(actualMessage, expectedMessage, "Empty field validation message is not displayed for the Confirm Password field!");
    }

    @Test(priority = 2,
        description = "Verify that the validation message is displayed when the user enters an email address in an invalid format.")
    public void verifyThatValidationMessageIsDisplayedWhenUserEntersAnEmailAddressInInvalidFormat() {
        expectedMessage = "Please enter a valid email address (Ex: johndoe@domain.com).";

        Reporter.log("Step 1: Enter an invalid email (Ex: test.domain@com), " +
                "Enter valid data in remaining fields, " +
                "Click on Create an Account button");
        registrationData.setEmail("test.domain@com");
        registerPO.registerWithRequiredDetails(registrationData);
        actualMessage = basePO.getValidationMessageByFieldName("Email");
        Assert.assertEquals(actualMessage, expectedMessage, "Invalid email address validation message is not displayed!");
    }

    @Test(priority = 3,
            description = "Verify that the user is not able to register when the passwords in the 'Password' and 'Confirm Password' fields are different and a 'Please enter the same value again' message is displayed.")
    public void verifyThatValidationMessageIsDisplayedWhenUserEntersDifferentPasswordsInPasswordAndConfirmPasswordFields() {
        expectedMessage = "Please enter the same value again.";

        Reporter.log("Step 1: Enter password in 'Password' field (Ex: Test@123), " +
                "Enter different password in 'Confirm Password' field (Ex: Test@456), " +
                "Enter valid data in remaining fields, " +
                "Click on Create an Account button", true);
        registrationData.setPassword("Test@123");
        registrationData.setConfirmPassword("Test@456");
        registerPO.registerWithRequiredDetails(registrationData);
        actualMessage = basePO.getValidationMessageByFieldName("Confirm Password");
        Assert.assertEquals(actualMessage, expectedMessage, "Password mis-match validation message is not displayed!");
    }

    @Test(priority = 4,
            description = "Verify that the user is not able to register with an email that is already registered and 'There is already an account with this email address' validation message is displayed.")
    public void verifyThatUserCannotRegisterWithAnAlreadyRegisteredEmailAndValidationMessageIsDisplayed() {
        expectedMessage = "There is already an account with this email address. If you are sure that it is your email address, click here to get your password and access your account.";

        Reporter.log("Step 1: Enter an already registered email, " +
                "Enter valid data in remaining fields, " +
                "Click on Create an Account button", true);
        registrationData.setEmail(Constants.DUPLICATE_EMAIL);
        registerPO.registerWithRequiredDetails(registrationData);
        actualMessage = registerPO.getErrorMessage();
        Assert.assertEquals(actualMessage, expectedMessage, "Duplicate account validation message is not displayed!");
    }

    @Test(priority = 5,
            description = "Verify that the user is able to register with valid data in all fields and is redirected to 'My Account' page with a 'Thank you for registering with Main Website Store' confirmation message.")
    public void verifyThatTheUserCanRegisterWithValidDetailsAndConfirmationMessageIsDisplayed() {
        expectedMessage = "Thank you for registering with Main Website Store.";

        Reporter.log("Step 1: Enter valid data in all fields, click on Create an Account button", true);
        registerPO.registerWithRequiredDetails(registrationData);
        actualMessage = registerPO.getActualSuccessMessage();
        Assert.assertEquals(actualMessage, expectedMessage, "User registration failed!");
        registerPO.storeValidUsers(registrationData);
    }
}
