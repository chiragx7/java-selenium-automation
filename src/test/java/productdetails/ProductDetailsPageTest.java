package productdetails;

import base.BaseTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.login.LoginPO;
import pageobjects.productdetails.ProductDetailsPO;
import pageobjects.productlist.ProductListPO;

public class ProductDetailsPageTest extends BaseTest {
    ProductDetailsPO productDetailsPO;
    ProductListPO productListPO;
    LoginPO loginPO;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        productDetailsPO = new ProductDetailsPO(driver);
        productListPO = new ProductListPO(driver);
        loginPO = new LoginPO(driver);

        Reporter.log("Navigate to Home Page", true);
        loginPO.navigateToHomePage();
        Assert.assertTrue(loginPO.isPageTitleDisplayed("Home Page"), "Home Page title is not displayed!");

        Reporter.log("Hover over 'Men' from the Main Navigation menu, " +
                "Hover over 'Tops' from the sub-menu, " +
                "Click on 'Jackets' from the sub-menu", true);
        productListPO.hoverOnMenThenHoverOnTopsAndClickOnTheJacketsFromTheMenu();
        Assert.assertTrue(loginPO.isPageTitleDisplayed("Jackets"), "Product Details Page title is not displayed!");

        Reporter.log("Click on a particular product", true);
        productDetailsPO.clickOnParticularProduct("Proteus Fitness Jackshirt");
    }

    @Test(priority = 1, description = "Verify that the user is redirected to the 'Home' page after clicking on 'Home' link from the breadcrumb navigation")
    public void verifyThatTheUserIsRedirectedToHomePageAfterClickingOnHomeLinkFromTheBreadcrumbNavigation() {
        Reporter.log("Step 1: Click on the 'Home' link", true);
        productDetailsPO.clickOnTheLinkFromBreadcrumb("Home");
        Assert.assertTrue(loginPO.isHomePageTitleDisplayed(), "Home Page title is not displayed");
    }
}
