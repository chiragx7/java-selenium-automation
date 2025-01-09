package utils;

import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.Objects;

public class SeleniumHelpers extends WaitHelpers {
    JavaHelpers helpers;
    @Getter
    Actions actions;

    public SeleniumHelpers(WebDriver driver) {
        super(driver);
        helpers = new JavaHelpers();
        actions = new Actions(driver);
    }

    public void click(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }

    public void type(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    public void enterText(WebElement element, String text) {
        waitForElementToBeVisible(element);
        actions.moveToElement(element).perform();
        element.clear();
        element.sendKeys(text);
    }

    public String getText(WebElement element) {
        waitForElementToBeVisible(element);
        return element.getText();
    }

    public void enterEmail(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    public void enterPassword(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    public void selectFromDropdown(WebElement dropdownElement, String value) {
        waitForElementToBeVisible(dropdownElement);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(value);

    }

    public String getAttribute(WebElement element, String attributeName) {
        waitForElementToBeVisible(element);
        return element.getDomAttribute(attributeName);
    }

    public boolean isDisplayed(WebElement element) {
        try {
            waitForElementToBeVisible(element);
            return element.isDisplayed();
        } catch (Exception e) {
            System.out.println("Error checking visibility of element: " + e.getMessage());
            return false;
        }
    }

    public void waitForPageLoad() {
        try {
            wait.until(webDriver -> Objects.equals(((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState"), "complete"));

            wait.until(webDriver -> Objects.requireNonNull(((JavascriptExecutor) webDriver)
                    .executeScript("return jQuery.active == 0")));
        } catch (Exception e) {
            System.out.println("Error waiting for page load: " + e.getMessage());
        }
    }
}
