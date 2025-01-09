package payment;

import base.BaseTest;
import datafactory.ShippingAddressData;
import dataobjects.AddressDetails;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.payment.PaymentPagePO;
import pageobjects.shipping.ShippingAddressPO;
import pageobjects.success.SuccessPagePO;

import java.util.stream.Stream;

public class PaymentPageTest extends BaseTest {
    PaymentPagePO paymentPagePO;
    SuccessPagePO successPagePO;
    ShippingAddressPO shippingAddressPO;
    AddressDetails addressDetails;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        paymentPagePO = new PaymentPagePO(driver);
        successPagePO = new SuccessPagePO(driver);
        shippingAddressPO = new ShippingAddressPO(driver);
        addressDetails = new ShippingAddressData().newAddressData();
    }

    @Test(priority = 1,
            description = "Verify that the 'Billing Address' form is displayed by unchecking the 'My billing and shipping address are the same' checkbox.",
            dependsOnMethods = "shipping.ShippingAddressTest.verifyThatTheUserIsRedirectedToPaymentMethodPageAfterClickOnNextButtonWithValidDataInAllRequiredFields")
    public void verifyThatBillingAddressFormIsDisplayedByUncheckingTheMyBillingAndShippingAddressAreTheSameCheckbox() {
        Reporter.log("Step 1: Uncheck 'My billing and shipping address are the same' checkbox", true);
        paymentPagePO.clickOnMyBillingAndShippingAddressAreTheSameCheckbox();
        Assert.assertTrue(paymentPagePO.isPaymentPageTitleDisplayed(), "Payment Page title is not displayed!");
    }

    @Test(priority = 2,
            description = "Ship To > Verify that after clicking on 'Edit' icon, the user is redirected to the 'Shipping Address' page, where new data can be entered and updated accordingly",
            dependsOnMethods = "shipping.ShippingAddressTest.verifyThatTheUserIsRedirectedToPaymentMethodPageAfterClickOnNextButtonWithValidDataInAllRequiredFields")
    public void verifyThatAfterClickingOnEditIconTheUserIsRedirectedToTheShippingAddressPageWhereNewDataCanBeEnteredAndUpdatedAccordingly() {
        String updatedShippingAddress;
        paymentPagePO.clickOnMyBillingAndShippingAddressAreTheSameCheckbox();

        Reporter.log("Step 1: Click on 'Edit' icon", true);
        paymentPagePO.clickOnShipToEditIcon();
        Assert.assertTrue(shippingAddressPO.isShippingAddressPageTitleDisplayed(), "Shipping address page title is not displayed!");

        Reporter.log("Step 2: Enter valid data in all fields, " +
                "Click on the 'Next' button", true);
        shippingAddressPO.fillNewShippingAddressForm(addressDetails);
        shippingAddressPO.clickOnNextButton();
        updatedShippingAddress = paymentPagePO.getUpdatedShippingAddress();
        Assert.assertTrue(Stream.of(
                addressDetails.getFirstName() + " " + addressDetails.getLastName(), addressDetails.getStreetAddress(),
                addressDetails.getCity(), addressDetails.getState(), addressDetails.getPostalCode(), addressDetails.getCountry(),
                addressDetails.getPhoneNumber()
        ).allMatch(updatedShippingAddress::contains), "Actual shipping address does not match the updated shipping address!");
    }

    @Test(priority = 3,
            description = "Verify that the user is redirected to 'Success' page after clicking on 'Place Order' button.",
            dependsOnMethods = "verifyThatAfterClickingOnEditIconTheUserIsRedirectedToTheShippingAddressPageWhereNewDataCanBeEnteredAndUpdatedAccordingly")
    public void verifyThatTheUserIsRedirectedToSuccessPageAfterClickingOnPlaceOrderButton() {
        Reporter.log("Step 1: Click on the Place Order button", true);
        paymentPagePO.clickOnPlaceOrderButton();
        Assert.assertTrue(successPagePO.isPageTitleDisplayed("Thank you for your purchase!"), "Order Confirmation Message is not displayed!");
    }
}
