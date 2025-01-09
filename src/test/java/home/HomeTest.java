package home;

import base.BaseTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.home.HomePO;
import pageobjects.shipping.ShippingAddressPO;

import java.util.Arrays;
import java.util.List;

public class HomeTest extends BaseTest {
    HomePO homePO;
    ShippingAddressPO shippingAddressPO;

    public HomeTest() {

    }

    @BeforeMethod
    public void setUp() {
        super.setUp();
        homePO = new HomePO(driver);
        shippingAddressPO = new ShippingAddressPO(driver);

        Reporter.log("Navigate to Home Page", true);
        homePO.navigateToHomePage();
        Assert.assertTrue(homePO.isPageTitleDisplayed("Home Page"), "Home Page title is not displayed!");
    }

    public void addProductToCart() {
        verifyThatUserCanAddProductToCartAndConfirmationMessageIsDisplayed();
    }

    @Test(priority = 1,
            description = "Verify that the navigation menu is displayed correctly and all menu items function as expected.")
    public void verifyThatNavigationMenuItemFunctionAsExpected() {
        List<String> navigationMenuItems = Arrays.asList("What's New", "Women", "Men", "Gear", "Training", "Sale");
        String actualNavigationMenuItemName;

        Reporter.log("Step 1: Click on every link of Navigation menu", true);
        for(String expectedNavigationMenuItemName : navigationMenuItems) {
            Reporter.log("Clicking on: "+expectedNavigationMenuItemName, true);
            homePO.clickOnEveryNavigationMenuItemsByLinkText(expectedNavigationMenuItemName);
            actualNavigationMenuItemName = homePO.getEveryPageTitle();
            Assert.assertEquals(actualNavigationMenuItemName, expectedNavigationMenuItemName, "Navigation Menu Item name and Page Title does not match!");
        }
    }

    @Test(priority = 2,
            description = "Verify that the user is able to add products to 'Cart' after clicking on 'Add to Cart' button and a confirmation message is displayed.")
    public void verifyThatUserCanAddProductToCartAndConfirmationMessageIsDisplayed() {
        String productName, selectedColor, selectedSize, actualSize, actualColor;

        Reporter.log("Step 1: Scroll to Hot Sellers section", true);
        homePO.scrollToHotSellersSection();
        Assert.assertTrue(homePO.isHotSellersSectionDisplayed(), "Hot Sellers section is not displayed!");

        Reporter.log("Step 2: Select size and color of a particular product", true);
        productName = homePO.getProductName();
        selectedSize = homePO.selectRandomSize(productName);
        selectedColor = homePO.selectRandomColor(productName);

        Reporter.log("Step 3: Click on the Add to Cart button", true);
        homePO.clickOnAddToCartButton(productName);
        String expectedSuccessMessage = "You added "+productName+" to your shopping cart.";
        String actualSuccessMessage = homePO.getActualSuccessMessage();
        Assert.assertEquals(actualSuccessMessage, expectedSuccessMessage, "Success message is not displayed!");

        Reporter.log("Step 4: Verify Correct Product is added in cart", true);
        homePO.clickOnCartIconIfCartIsNotActive();
        homePO.clickOnSeeDetailsText();
        actualSize = homePO.getSizeOrColorOfTheProductAddedInCart("Size");
        actualColor = homePO.getSizeOrColorOfTheProductAddedInCart("Color");
        System.out.println("Actual Size: "+actualSize+"\nSelected Size: "+selectedSize+"\nActual Color: "+actualColor+"\nSelected color: "+selectedColor);
        Assert.assertTrue(homePO.isProductAddedToCartDisplayed(productName), "Product name does not match the actual product!");
        Assert.assertEquals(actualSize, selectedSize, "Actual and Selected size does not match!");
        Assert.assertEquals(actualColor, selectedColor, "Actual and Selected color does not match!");
    }

    @Test(priority = 3,
            description = "Cart > Verify that the user is redirected to 'Product checkout' page after clicking on 'Proceed to Checkout' button.")
    public void verifyThatUserIsRedirectedToProductCheckoutPageAfterClickingOnProceedToCheckoutButton() {
        Reporter.log("Step 1: Click on the Cart icon", true);
        homePO.clickOnCartIconIfCartIsNotActive();
        Assert.assertFalse(homePO.isCartEmpty(), "Cart is Empty!");

        Reporter.log("Step 2: Click on the Proceed to Checkout button", true);
        homePO.clickOnProceedToCheckoutButton();
        Assert.assertTrue(shippingAddressPO.isShippingAddressPageTitleDisplayed(), "Shipping page title is not displayed!");
    }

    @Test(priority = 4, description = "Cart > Verify that the user can delete product after clicking on 'Delete' icon.")
    public void verifyThatUserCanDeleteProductFromTheCartAfterClickingOnTheDeleteIcon() {

        Reporter.log("Step 1: Click on the Cart icon", true);
        homePO.clickOnCartIconIfCartIsNotActive();
        Assert.assertFalse(homePO.isCartEmpty(), "Cart is Empty!");

        Reporter.log("Step 2: Click on the Delete icon", true);
        homePO.clickOnDeleteIconInCart();
        Assert.assertTrue(homePO.isDialogDisplayed(), "Confirmation dialog is not displayed!");

        Reporter.log("Step 3: Click on the Ok button", true);
        homePO.clickOnDialogOkButton();
        Assert.assertTrue(homePO.isCartEmpty(), "Product is not removed from the cart!");
    }

    @Test(priority = 5,
            description = "Cart > Verify that the 'The requested qty is not available' alert box is displayed when the user enters a quantity in the 'Qty' field that is greater than the available stock.")
    public void verifyThatAlertBoxIsDisplayedWhenUserEntersQuantityInThtQtyFieldThatIsGreaterThanTheAvailableStock() {
        String expectedAlertBoxMessage = "The requested qty exceeds the maximum qty allowed in shopping cart";
        String actualAlertBoxMessage;

        Reporter.log("Step 1: Add a particular product in the Cart", true);
        verifyThatUserCanAddProductToCartAndConfirmationMessageIsDisplayed();

        Reporter.log("Step 2: Click on the Cart icon", true);
        homePO.clickOnCartIconIfCartIsNotActive();
        Assert.assertFalse(homePO.isCartEmpty(), "Cart is Empty!");

        Reporter.log("Step 3: Update quantity (Ex: 10001) in 'Qty' field.", true);
        homePO.enterQuantityInQuantityField("10001");
        Assert.assertTrue(homePO.isCartUpdateButtonDisplayed(), "Update button in cart is not displayed!");

        Reporter.log("Step 4: Click on the Update button", true);
        homePO.clickOnCartUpdateButton();
        actualAlertBoxMessage = homePO.getMaximumQuantityMessage();
        homePO.clickOnDialogOkButton();
        Assert.assertEquals(actualAlertBoxMessage, expectedAlertBoxMessage, "Alert box message is not displayed!");
    }
}
