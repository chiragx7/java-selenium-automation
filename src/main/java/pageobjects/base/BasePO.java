package pageobjects.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Constants;
import utils.SeleniumHelpers;

public class BasePO {
    protected WebDriver driver;
    protected SeleniumHelpers selenium;

    @FindBy(xpath = "//h1[@class='page-title']")
    private WebElement homePageTitle;

    String pageTitleXpath = "//h1/span[text()='%s']";
    String getValidationMessageByFieldNameXpath = "//span[text()='%s']/parent::label/following-sibling::div/div";

    public BasePO(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
    }
        this.driver = driver;
        this.selenium = new SeleniumHelpers(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isPageTitleDisplayed(String pageName) {
        return selenium.isDisplayed(driver.findElement(By.xpath(String.format(pageTitleXpath, pageName))));
    }

    public boolean isHomePageTitleDisplayed() {
        return selenium.isDisplayed(homePageTitle) && selenium.getText(homePageTitle).equals("Home Page");
    }

    public String getValidationMessageByFieldName(String fieldName) {
        return selenium.getText(driver.findElement(By.xpath(String.format(getValidationMessageByFieldNameXpath, fieldName))));
    }

    public void navigateToHomePage() {
        if(!isHomePageTitleDisplayed()) {
            driver.get(Constants.BASE_URL);
        }
    }
}
