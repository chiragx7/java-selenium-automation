package pageobjects.shipping;

import datafactory.LoginData;
import datafactory.RegistrationData;
import dataobjects.AddressDetails;
import dataobjects.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;
import pageobjects.login.LoginPO;

@Slf4j
public class ShippingAddressPO extends BasePO {
    LoginPO loginPO;
    UserDetails userDetails;
    public ShippingAddressPO(WebDriver driver) {
        super(driver);
        loginPO = new LoginPO(driver);
        userDetails = new LoginData().loginData();
    }

    @FindBy(xpath = "//div[text()='Shipping Address']")
    private WebElement shippingAddressPageTitle;

    @FindBy(xpath = "//div[contains(@class,'shipping-address-item selected-item')]")
    private WebElement preShippingAddressForm;

    @FindBy(xpath = "//div[@id='opc-new-shipping-address']")
    private WebElement newShippingAddressFormTitle;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='firstname']")
    private WebElement firstNameField;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='lastname']")
    private WebElement lastNameField;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='street[0]']")
    private WebElement streetAddressField;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='city']")
    private WebElement cityField;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//select[@name='region_id']")
    private WebElement stateDropdown;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='postcode']")
    private WebElement postalCodeField;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//select[@name='country_id']")
    private WebElement countryDropdown;

    @FindBy(xpath = "//div[@id='shipping-new-address-form']//input[@name='telephone']")
    private WebElement phoneNumberField;

    @FindBy(xpath = "//button[contains(@class,'continue')]")
    private WebElement nextButton;

    @FindBy(xpath = "//button[contains(@class,'action-save-address')]")
    private WebElement shipHereButton;

    @FindBy(xpath = "//fieldset[@id='customer-email-fieldset']//input[@type='email']")
    private WebElement emailField;

    @FindBy(xpath = "//fieldset[@id='customer-email-fieldset']//input[@type='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(@class,'login primary')]")
    private WebElement loginButton;

    @FindBy(xpath = "//div[text()='Shipping Address']")
    private WebElement shippingPageTitle;

    @FindBy(xpath = "//aside[contains(@class,'authentication-dropdown')]")
    private WebElement signInFormPopUp;

    @FindBy(xpath = "//button[contains(@class,'action-auth-toggle')]")
    private WebElement signInLink;

    @FindBy(xpath = "//div[@class='new-address-popup']/button")
    private WebElement newAddressButton;

    @FindBy(xpath = "//li[@id='opc-shipping_method']//div[@class='loader']")
    private WebElement loader;

    String radioButtonXpath = "//td[text()='%s']/../td/input";

    private WebElement getRadioButton(String radioButtonName) {
        return driver.findElement(By.xpath(String.format(radioButtonXpath, radioButtonName)));
    }

    public boolean isShippingAddressPageTitleDisplayed() {
        return selenium.isDisplayed(shippingAddressPageTitle);
    }

    public boolean isSignInFormPopUpDisplayed() {
        return selenium.isDisplayed(signInFormPopUp);
    }

    public void fillShippingAddress(AddressDetails addressDetails) {
        userDetails = new RegistrationData().registrationData();
        String userName = userDetails.getFirstName() + " " + userDetails.getLastName();
        if(!loginPO.isLoginSuccessful(userName)) {
            selenium.type(emailField, addressDetails.getEmail());
            selenium.type(passwordField, addressDetails.getPassword());
            selenium.click(loginButton);
        }
        if(!selenium.isDisplayed(preShippingAddressForm)) {
            selenium.waitForPageLoad();
            selenium.type(firstNameField, addressDetails.getFirstName());
            selenium.type(lastNameField, addressDetails.getLastName());
            selenium.type(streetAddressField, addressDetails.getStreetAddress());
            selenium.type(cityField, addressDetails.getCity());
            selenium.selectFromDropdown(countryDropdown, addressDetails.getCountry());
            selenium.selectFromDropdown(stateDropdown, addressDetails.getState());
            selenium.type(postalCodeField, addressDetails.getPostalCode());
            selenium.type(phoneNumberField, addressDetails.getPhoneNumber());
            selenium.click(getRadioButton("Fixed"));
            clickOnNextButton();
        } else {
            clickOnNextButton();
        }
    }

    public void fillNewShippingAddressForm(AddressDetails addressDetails) {
        if(selenium.isDisplayed(newAddressButton)) {
            clickOnNewAddressButton();
        }

        selenium.type(firstNameField, addressDetails.getFirstName());
        selenium.type(lastNameField, addressDetails.getLastName());
        selenium.type(streetAddressField, addressDetails.getStreetAddress());
        selenium.type(cityField, addressDetails.getCity());
        selenium.selectFromDropdown(countryDropdown, addressDetails.getCountry());
        selenium.selectFromDropdown(stateDropdown, addressDetails.getState());
        selenium.type(postalCodeField, addressDetails.getPostalCode());
        selenium.type(phoneNumberField, addressDetails.getPhoneNumber());

        if(selenium.isDisplayed(shipHereButton)) {
            clickOnShipHereButton();
        }

    }

    public void clickOnNextButton() {
        selenium.click(nextButton);
    }

    public void clickOnShipHereButton() {
        selenium.click(shipHereButton);
    }

    public void clickOnSignInLink() {
        selenium.click(signInLink);
    }

    public void clickOnNewAddressButton() {
        selenium.click(newAddressButton);
    }
}
