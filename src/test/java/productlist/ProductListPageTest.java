package productlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.login.LoginPO;
import pageobjects.productlist.ProductListPO;
import utils.Constants;

public class ProductListPageTest extends BaseTest {
    ProductListPO productListPO;
    LoginPO loginPO;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        productListPO = new ProductListPO(driver);
        loginPO = new LoginPO(driver);

        Reporter.log("Navigate to Home Page", true);
        loginPO.navigateToHomePage();
        Assert.assertTrue(loginPO.isPageTitleDisplayed("Home Page"), "Home Page title is not displayed!");

        Reporter.log("Hover over 'Men' from the Main Navigation menu, " +
                "Click on 'Tops' from the sub-menu", true);
        productListPO.hoverOverMenAndClickOnTopsFromTheMenu();
        Assert.assertTrue(productListPO.isPageTitleDisplayed("Tops"), "Product List Page title is not displayed!");
    }

    @Test(priority = 1, description = "Verify that the user is redirected to next 'Product List' page after clicking on 'Next' icon")
    public void verifyThatTheUserIsRedirectedToNexProductListPageAfterClickingOnTheNextIcon() {
        String expectedPageUrl;
        String actualPageUrl;

        Reporter.log("Step 1: Click on the 'Next' icon", true);
        productListPO.clickOnNextPageButton();
        expectedPageUrl = Constants.PRODUCT_LIST_PAGE_URL+productListPO.getCurrentPageIndex();
        actualPageUrl = productListPO.getCurrentURL();
        Assert.assertEquals(actualPageUrl, expectedPageUrl, "Page number does not match!");
    }
}
