package pageobjects.productdetails;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.base.BasePO;

public class ProductDetailsPO extends BasePO {
    public ProductDetailsPO(WebDriver driver) {
        super(driver);
    }

    String breadCrumbXpath = "//ul[@class='items']//a[contains(text(),'%s')]";
    String productNameLinkXpath = "//a[@class='product-item-link' and contains(text(),'Cassius Sparring Tank')]";

    public void clickOnParticularProduct(String productName) {
        selenium.click(driver.findElement(By.xpath(String.format(productNameLinkXpath, productName))));
    }

    public void clickOnTheLinkFromBreadcrumb(String linkName) {
        selenium.click(driver.findElement(By.xpath(String.format(breadCrumbXpath, linkName))));
    }
}
