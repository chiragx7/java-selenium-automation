package login;

import base.BaseTest;
import datafactory.LoginData;
import datafactory.RegistrationData;
import dataobjects.UserDetails;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.base.BasePO;
import pageobjects.login.LoginPO;

public class LoginTest extends BaseTest {
    BasePO basePO;
    LoginPO loginPO;
    UserDetails userDetails;
    String actualValidationMessage;
    String expectedValidationMessage;

    @BeforeClass
    public void checkIsUserLoggedIn() {
        super.setUp();
        basePO = new BasePO(driver);
        loginPO = new LoginPO(driver);
        loginPO.ensureUserIsSignedOut();
    }

    @BeforeMethod
    public void setUp() {
        userDetails = new LoginData().loginData();

        Reporter.log("Navigate to Home Page", true);
        basePO.navigateToHomePage();
        Assert.assertTrue(basePO.isPageTitleDisplayed("Home Page"), "Home Page title is not displayed!");

        Reporter.log("Click on the Sign In Link", true);
        loginPO.clickSignInLink();
        Assert.assertTrue(basePO.isPageTitleDisplayed("Customer Login"), "Login Page title is not displayed!");
    }

    @Test(description = "Verify that 'This is a required field' message is displayed when email and password fields are blank.",
            priority = 1)
    public void verifyThatValidationMessageIsDisplayedWhenEmailAndPasswordFieldsAreBlank() {
        expectedValidationMessage = "This is a required field.";

        Reporter.log("Step 1: Click on the Sign In button", true);
        loginPO.clickOnLoginButton();
        actualValidationMessage = basePO.getValidationMessageByFieldName("Email");
        Assert.assertEquals(actualValidationMessage, expectedValidationMessage, "Empty field validation message is not displayed for the Email field!");

        Reporter.log("Step 2: Enter email address in Email field, click on the Sign In button", true);
        loginPO.enterEmail(userDetails.getEmail());
        loginPO.clickOnLoginButton();
        actualValidationMessage = basePO.getValidationMessageByFieldName("Password");
        Assert.assertEquals(actualValidationMessage, expectedValidationMessage, "Empty field validation message is not displayed for the Password field!");
    }

    @Test(description = "Verify that the validation message is displayed when the user enters an email address in an invalid format.",
            priority = 2)
    public void verifyThatValidationMessageIsDisplayedWhenUserEntersAnEmailAddressInInvalidFormat() {
        expectedValidationMessage = "Please enter a valid email address (Ex: johndoe@domain.com).";

        Reporter.log("Step 1: Enter an invalid email (Ex: test.domain@com), " +
                "Enter password in Password field, " +
                "Click on the Sign In button", true);
        userDetails.setEmail("test.domain@com");
        loginPO.loginWithCredentials(userDetails);
        actualValidationMessage = basePO.getValidationMessageByFieldName("Email");
        Assert.assertEquals(actualValidationMessage, expectedValidationMessage, "Invalid email validation message is not displayed!");
    }

    @Test(description = "Verify that the user is not able to login with invalid email and password.",
            priority = 3)
    public void verifyThatTheUserCannotLoginWithInvalidCredentials() {
        UserDetails invalidLoginData = new LoginData().invalidLoginData();

        Reporter.log("Step 1: Enter invalid credentials, click on the Sign In button", true);
        loginPO.loginWithCredentials(invalidLoginData);
        String actualErrorMessage = loginPO.getLoginFailedValidationMessageDisplayed();
        String expectedErrorMessage = "The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.";
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Invalid credentials validation message is not displayed!");
    }

    @Test(description = "Verify that the user is able to login with a valid email and password.",
            priority = 4)
    public void verifyThatTheUserCanLoginWithValidCredentials() {
        UserDetails registerDetails = new RegistrationData().registrationData();
        String userName = registerDetails.getFirstName() + " " + registerDetails.getLastName();

        Reporter.log("Step 1: Enter valid credentials, click on the Sign In button", true);
        loginPO.loginWithCredentials(userDetails);
        Assert.assertTrue(loginPO.isLoginSuccessful(userName), "Login failed using "+userDetails.getEmail());
    }
}
