package pageobjects.login;

import datafactory.RegistrationData;
import dataobjects.UserDetails;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class LoginPO extends BasePO {
    public LoginPO(WebDriver driver) {
        super(driver);
        ensureUserIsSignedOut();
    }

    @FindBy(xpath = "//h1[@class='page-title']")
    private WebElement homePageTitle;

    @FindBy(xpath = "//div[@class='panel header']/ul/li[@class='authorization-link']/a")
    private WebElement signInLink;

    @FindBy(xpath = "//a[contains(@class,'remind')]")
    private WebElement forgotYourPasswordLink;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "pass")
    private WebElement passwordField;

    @FindBy(id = "send2")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='panel header']/ul/li/span[@class='logged-in']")
    private WebElement userNameLabel;

    @FindBy(xpath = "//div[contains(@class,'message-error')]/div")
    private WebElement errorMessageLabel;

    @FindBy(xpath = "//button[@data-action='customer-menu-toggle']")
    private WebElement customerMenuToggle;

    @FindBy(xpath = "//span[contains(@class, 'active')]/following::a[3]")
    private WebElement signOutLink;

    public boolean isLoginSuccessful(String userName) {
        return selenium.isDisplayed(userNameLabel) && selenium.getText(userNameLabel).contains(userName);
    }

    public String getLoginFailedValidationMessageDisplayed() {
        return selenium.getText(errorMessageLabel);
    }

    public void ensureUserIsSignedOut() {
        UserDetails userDetails = new RegistrationData().registrationData();
        String userName = userDetails.getFirstName() + " " + userDetails.getLastName();
        try {
            if(isLoginSuccessful(userName)) {
                signOutUser();
                selenium.waitForElementToBeVisible(homePageTitle);
                selenium.waitForElementToBeClickable(signInLink);
            }
        } catch (Exception e) {
            System.out.println("Error during sign out: " + e.getMessage());
        }
    }

    public void signOutUser() {
        selenium.click(customerMenuToggle);
        selenium.click(signOutLink);
    }

    public void clickSignInLink() {
        signInLink.click();
    }

    public void clickOnLoginButton() {
        selenium.waitForElementToBeClickable(loginButton);
        selenium.click(loginButton);
    }

    public void enterEmail(String email) {
        selenium.enterEmail(emailField, email);
    }

    public void enterPassword(String password) {
        selenium.enterEmail(passwordField, password);
    }

    public void loginWithCredentials(UserDetails  userDetails) {
        if(!isPageTitleDisplayed("Customer Login")) {
            selenium.click(signInLink);
        }
        enterEmail(userDetails.getEmail());
        enterPassword(userDetails.getPassword());
        clickOnLoginButton();
    }

    public void clickOnForgotYourPasswordLink() {
        selenium.click(forgotYourPasswordLink);
    }
}
