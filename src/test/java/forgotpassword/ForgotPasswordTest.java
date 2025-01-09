package forgotpassword;

import base.BaseTest;
import datafactory.LoginData;
import dataobjects.UserDetails;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.home.HomePO;
import pageobjects.login.LoginPO;
import pageobjects.forgotpassword.ForgotPasswordPO;

public class ForgotPasswordTest extends BaseTest {
    ForgotPasswordPO forgotPasswordPO;
    LoginPO loginPO;
    UserDetails userDetails;
    HomePO homePO;
    String expectedMessage;
    String actualMessage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        forgotPasswordPO = new ForgotPasswordPO(driver);
        loginPO = new LoginPO(driver);
        homePO = new HomePO(driver);
        userDetails = new LoginData().loginData();

        Reporter.log("Navigate to Home Page", true);
        loginPO.navigateToHomePage();
        Assert.assertTrue(homePO.isPageTitleDisplayed("Home Page"), "Home Page title is not displayed!");

        Reporter.log("Click on Sign In link", true);
        loginPO.clickSignInLink();
        Assert.assertTrue(homePO.isPageTitleDisplayed("Customer Login"), "Login Page title is not displayed!");

        Reporter.log("Click on Forgot Your Password? link", true);
        loginPO.clickOnForgotYourPasswordLink();
        Assert.assertTrue(homePO.isPageTitleDisplayed("Forgot Your Password?"), "Forgot Your Password Page title is not displayed!");
    }

    @Test(priority = 1, description = "Verify that validation message is displayed when the 'Email' field is blank.")
    public void verifyThatValidationMessageIsDisplayedWhenTheEmailFieldIsBlank() {
        expectedMessage = "This is a required field.";

        Reporter.log("Step 1: Click on 'Reset My Password' button", true);
        forgotPasswordPO.clickOnResetMyPasswordButton();
        actualMessage = homePO.getValidationMessageByFieldName("Email");
        Assert.assertEquals(actualMessage, expectedMessage, "The required field validation message is not displayed!");
    }

    @Test(priority = 2, description = "Verify that the user is redirected to 'Customer Login' page with a confirmation message after clicking on 'Reset My Password' button.")
    public void verifyThatTheUserIsRedirectedToCustomerLoginPageWithConfirmationMessageAfterClickingOnResetMyPasswordButton() {
        expectedMessage = "If there is an account associated with "+userDetails.getEmail()+" you will receive an email with a link to reset your password.";

        Reporter.log("Step 1: Enter a valid email address, Click on Reset My Password button", true);
        forgotPasswordPO.enterEmail(userDetails.getEmail());
        forgotPasswordPO.clickOnResetMyPasswordButton();
        actualMessage = homePO.getActualSuccessMessage();
        Assert.assertTrue(homePO.isPageTitleDisplayed("Customer Login"), "Login page title is not displayed!");
        Assert.assertEquals(actualMessage, expectedMessage, "Confirmation message is not displayed!");
    }
}
