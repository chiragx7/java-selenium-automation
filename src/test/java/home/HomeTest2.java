package home;

import base.BaseTest;
import datafactory.LoginData;
import dataobjects.UserDetails;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.home.HomePO;
import pageobjects.login.LoginPO;

public class HomeTest2 extends BaseTest {
    HomePO homePO;
    LoginPO loginPO;
    UserDetails userDetails;
    String actualMessage;
    String expectedMessage;
    String productName;

    @BeforeClass
    public void init() {
        super.setUp();
        homePO = new HomePO(driver);
        this.productName = homePO.getProductName();
    }

    @BeforeMethod
    public void setUp() {
        loginPO = new LoginPO(driver);
        userDetails = new LoginData().loginData();

        Reporter.log("Navigate to Home Page", true);
        homePO.navigateToHomePage();
        Assert.assertTrue(homePO.isPageTitleDisplayed("Home Page"), "Home Page title is not displayed!");
    }

    @Test(priority = 1,
            description = "Hot Sellers > Verify that the user is redirected to the 'Customer Login' page after clicking on the 'Add to Wish List' icon when the user is not logged in, and the 'You must login or register to add items to your wishlist' validation message is displayed.")
    public void verifyThatTheUserIsRedirectedToCustomerLoginPageAfterClickingOnAddToWishListIconWhenTheUserIsNotLoggedIn() {
        expectedMessage = "You must login or register to add items to your wishlist.";

        Reporter.log("Step 1: Scroll to Hot Sellers section", true);
        homePO.scrollToHotSellersSection();
        Assert.assertTrue(homePO.isHotSellersSectionDisplayed(), "Hot Sellers section is not displayed!");

        Reporter.log("Step 2: Hover on a particular product", true);
        homePO.hoverOnParticularProduct();
        Assert.assertTrue(homePO.isWishListIconVisible(productName), "Wish List icon is not displayed!");

        Reporter.log("Step 3: Click on Add to Wish List icon", true);
        homePO.clickOnAddToWishListIcon();
        actualMessage = loginPO.getLoginFailedValidationMessageDisplayed();
        Assert.assertTrue(homePO.isPageTitleDisplayed("Customer Login"), "Login page title is not displayed!");
        Assert.assertEquals(actualMessage, expectedMessage, "User not logged in error message is not displayed!");
    }

    @Test(priority = 2,
            description = "Hot Sellers > Verify that the product is added to the Wishlist when the user clicks on the 'Add to Wish List' icon after login.")
    public void verifyThatTheLoggedInUserCanAddProductToWishListAfterClickingOnAddToWishListIcon() {

        Reporter.log("Step 1: Add a particular product to Wish List", true);
        verifyThatTheUserIsRedirectedToCustomerLoginPageAfterClickingOnAddToWishListIconWhenTheUserIsNotLoggedIn();
        Assert.assertTrue(homePO.isPageTitleDisplayed("Customer Login"), "Login page title is not displayed");

        Reporter.log("Step 2: Login with valid credentials", true);
        loginPO.loginWithCredentials(userDetails);
        actualMessage = homePO.getActualSuccessMessage();
        expectedMessage = productName+" has been added to your Wish List. Click here to continue shopping.";
        Assert.assertEquals(actualMessage, expectedMessage, "Wish List confirmation message is not displayed!");
    }
}
