package shipping;

import base.BaseTest;
import datafactory.ShippingAddressData;
import dataobjects.AddressDetails;
import home.HomeTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.home.HomePO;
import pageobjects.payment.PaymentPagePO;
import pageobjects.shipping.ShippingAddressPO;

public class ShippingAddressTest extends BaseTest {
    HomePO homePO;
    HomeTest homeTest;
    ShippingAddressPO shippingAddressPO;
    PaymentPagePO paymentPagePO;
    AddressDetails addressDetails;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        homePO = new HomePO(driver);
        homeTest = new HomeTest();
        homeTest.setUp();
        shippingAddressPO = new ShippingAddressPO(driver);
        paymentPagePO = new PaymentPagePO(driver);
        addressDetails = new ShippingAddressData().addressData();

        Reporter.log("Navigate to Home Page", true);
        homePO.navigateToHomePage();
        Assert.assertTrue(homePO.isPageTitleDisplayed("Home Page"), "Home Page title is not displayed!");

        Reporter.log("Add a particular product in the Cart", true);
        homeTest.addProductToCart();

        Reporter.log("Click on the Cart icon", true);
        homePO.clickOnCartIconIfCartIsNotActive();
        Assert.assertFalse(homePO.isCartEmpty(), "Cart is Empty!");

        Reporter.log("Click on the Proceed to Checkout button", true);
        homePO.clickOnProceedToCheckoutButton();
        Assert.assertTrue(shippingAddressPO.isShippingAddressPageTitleDisplayed(), "Shipping page title is not displayed!");
    }

    @Test(priority = 1, description = "User not logged in > Verify that 'Sign In' form pop-up is opened when the user clicks on 'Sign In' link in header.")
    public void VerifyThatSignInFormPopUpIsOpenedWhenTheUserClicksOnTheSignInLinkInHeader() {
        Reporter.log("Step 1: Click on the 'Sign In' link in header", true);
        shippingAddressPO.clickOnSignInLink();
        Assert.assertTrue(shippingAddressPO.isSignInFormPopUpDisplayed(), "Sign In form pop-up is not displayed!");
    }

    @Test(priority = 2,
            description = "Verify that the user is redirected to 'Payment Method' page after clicking on 'Next' with valid data in required fields and blank data in optional fields.")
    public void verifyThatTheUserIsRedirectedToPaymentMethodPageAfterClickOnNextButtonWithValidDataInAllRequiredFields() {
        Reporter.log("Step 1: Enter valid data in required fields, Click on Next button", true);
        shippingAddressPO.fillShippingAddress(addressDetails);
        Assert.assertTrue(paymentPagePO.isPaymentPageTitleDisplayed(), "Payment page title is not displayed!");
    }
}
