package pageobjects.productlist;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ProductListPO extends BasePO {
    public ProductListPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "(//a[@title='Next'])[2]")
    private WebElement nextPageButton;

    @FindBy(xpath = "//span[text()='Men']")
    private WebElement menOptionFromNavigationMenu;

    @FindBy(xpath = "//span[text()='Men']/following::a/span[text()='Tops']")
    private WebElement topsFromSubMenuOfMen;

    @FindBy(xpath = "//span[text()='Men']/following::a/span[text()='Jackets']")
    private WebElement jacketsFromSubMenuOfTops;

    @FindBy(xpath = "(//span[contains(text(),'currently')]/following-sibling::span)[2]")
    private WebElement currentPageIndex;

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public String getCurrentPageIndex() {
        return selenium.getText(currentPageIndex);
    }

    public void clickOnNextPageButton() {
        selenium.click(nextPageButton);
    }

    public void hoverOverMenAndClickOnTopsFromTheMenu() {
        selenium.getActions().moveToElement(menOptionFromNavigationMenu).perform();
        selenium.getActions().moveToElement(topsFromSubMenuOfMen).perform();
        selenium.click(topsFromSubMenuOfMen);
    }

    public void hoverOnMenThenHoverOnTopsAndClickOnTheJacketsFromTheMenu() {
        selenium.getActions().moveToElement(menOptionFromNavigationMenu).perform();
        selenium.getActions().moveToElement(topsFromSubMenuOfMen).perform();
        selenium.getActions().moveToElement(jacketsFromSubMenuOfTops).perform();
        selenium.click(jacketsFromSubMenuOfTops);
    }
}
