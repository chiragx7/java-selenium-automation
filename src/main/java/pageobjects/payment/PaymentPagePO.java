package pageobjects.payment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class PaymentPagePO extends BasePO {
    public PaymentPagePO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[text()='Payment Method']")
    private WebElement paymentPageTitle;

    @FindBy(xpath = "//input[@name='billing-address-same-as-shipping']")
    private WebElement myBillingAndShippingAddressAreTheSameCheckbox;

    @FindBy(xpath = "//select[@name='billing_address_id']")
    private WebElement myBillingAndShippingAddressAreTheSameDropdown;

    @FindBy(xpath = "//option[contains(text(),'New Address')]")
    private WebElement newAddressOption;

    @FindBy(xpath = "//div[@class='billing-address-form']")
    private WebElement billingAddressForm;

    @FindBy(xpath = "//button[contains(@class,'checkout')]")
    private WebElement placeOrderButton;

    @FindBy(xpath = "//div[@class='ship-to']//button")
    private WebElement shipToEditIcon;

    @FindBy(xpath = "//div[@class='ship-to']/div[@class='shipping-information-content']")
    private WebElement updatedShippingAddress;

    public boolean isPaymentPageTitleDisplayed() {
        return selenium.isDisplayed(paymentPageTitle);
    }

    public boolean isBillingAddressFormDisplayed() {
        return selenium.isDisplayed(billingAddressForm);
    }

    public String getUpdatedShippingAddress() {
        return selenium.getText(updatedShippingAddress);
    }

    public void clickOnMyBillingAndShippingAddressAreTheSameCheckbox() {
        while (!isBillingAddressFormDisplayed()) {
            selenium.click(myBillingAndShippingAddressAreTheSameCheckbox);
            if(selenium.isDisplayed(myBillingAndShippingAddressAreTheSameDropdown)) {
                selenium.click(myBillingAndShippingAddressAreTheSameCheckbox);
                break;
            }
        }
    }

    public void closeBillingAddressForm() {
        while (selenium.isDisplayed(billingAddressForm)) {
            selenium.click(myBillingAndShippingAddressAreTheSameCheckbox);
        }
    }

    public void clickOnPlaceOrderButton() {
        closeBillingAddressForm();
        selenium.click(placeOrderButton);
        selenium.waitForPageLoad();
    }

    public void clickOnShipToEditIcon() {
        selenium.click(shipToEditIcon);
    }
}
