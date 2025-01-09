package cart;

import base.BaseTest;
import home.HomeTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.cart.ShoppingCartPO;
import pageobjects.home.HomePO;
import utils.Constants;

public class ShoppingCartTest extends BaseTest {
    ShoppingCartPO shoppingCartPO;
    HomeTest homeTest;
    HomePO homePO;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        shoppingCartPO = new ShoppingCartPO(driver);
        homePO = new HomePO(driver);
        homeTest = new HomeTest();
        homeTest.setUp();

        Reporter.log("Navigate to Home Page", true);
        Assert.assertTrue(homePO.isPageTitleDisplayed("Home Page"), "Home Page title is not displayed!");

        Reporter.log("Add a particular product to cart", true);
        homeTest.addProductToCart();

        Reporter.log("Click on the Cart icon", true);
        homePO.clickOnCartIconIfCartIsNotActive();
        Assert.assertFalse(homePO.isCartEmpty(), "Cart is Empty!");

        Reporter.log("Click on the View and Edit Cart link", true);
        homePO.clickOnViewAndEditCartLink();
        Assert.assertTrue(homePO.isPageTitleDisplayed("Shopping Cart"), "Shopping Cart page title is not displayed!");
    }

    @Test(priority = 1,
            description = "Verify that the user is able to expand 'Estimate Shipping and Tax' section after clicking on 'Estimate Shipping and Tax' section.")
    public void verifyThatTheUserCanExpandEstimateShippingAndTaxSectionAfterClickingOnEstimateShippingAndTaxSection() {
        Reporter.log("Step 1: Click on 'Estimate Shipping and Tax' text", true);
        shoppingCartPO.clickOnEstimateShippingAndTaxText();
        Assert.assertTrue(shoppingCartPO.isExpandedEstimateShippingAndTaxSectionDisplayed(), "Estimate Shipping and Tax section is not displayed!");
    }

    @Test(priority = 2,
            description = "Verify that validation message is displayed when invalid discount code is entered.")
    public void verifyThatValidationMessageIsDisplayedWhenInvalidDiscountCodeIsEntered() {
        String actualErrorMessage;
        String expectedErrorMessage = "The coupon code \"" + Constants.INVALID_DISCOUNT_CODE + "\" is not valid.";

        Reporter.log("Step 1: Click on 'Apply Discount Code' link", true);
        shoppingCartPO.clickOnDiscountCodeLink();
        Assert.assertTrue(shoppingCartPO.isDiscountCodeInputFieldDisplayed(), "Discount code input field is not displayed!");

        Reporter.log("Step 2: Enter invalid discount code in 'Enter a discount code' field, Click on 'Apply Discount' button", true);
        shoppingCartPO.enterDiscountCode();
        shoppingCartPO.clickOnApplyDiscountButton();
        actualErrorMessage = shoppingCartPO.getActualErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Invalid discount code validation message is not displayed!");
    }

    @Test(priority = 3,
            description = "Estimate Shipping and Tax > Verify that when the user selects a country without a predefined state list from the 'Country' dropdown, " +
            "the 'State/Province' field is changed from a dropdown to a text field, " +
            "and the 'Best Way' option is not displayed for any country other than the USA")
    public void verifyThatTheStateFieldChangesFromDropdownToTextFieldWhenTheUserSelectsACountryThatDoesNotHavePredefinedStateList() {
        Reporter.log("Step 1: Click on 'Estimate Shipping and Tax' text", true);
        shoppingCartPO.clickOnEstimateShippingAndTaxText();
        Assert.assertTrue(shoppingCartPO.isExpandedEstimateShippingAndTaxSectionDisplayed(), "Estimate Shipping and Tax section is not displayed!");

        Reporter.log("Step 2: Select 'Botswana' option from the 'Country' dropdown.", true);
        shoppingCartPO.selectFromDropdown();
        Assert.assertTrue(shoppingCartPO.isStateInputFieldDisplayed(), "Dropdown field is not changed to a Text field!");
        Assert.assertFalse(shoppingCartPO.isBestWayOptionDisplayed(), "Best Way option is displayed!");
    }
}
