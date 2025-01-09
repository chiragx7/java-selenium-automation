package pageobjects.cart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;
import utils.Constants;

public class ShoppingCartPO extends BasePO {
    public ShoppingCartPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//strong[@id='block-shipping-heading']")
    private WebElement estimateShippingAndTaxText;

    @FindBy(xpath = "//div[@id='block-summary']")
    private WebElement expandedEstimateShippingAndTaxSection;

    @FindBy(xpath = "//select[@name='country_id']")
    private WebElement countryDropdown;

    @FindBy(xpath = "//input[@name='region']")
    private WebElement stateInputField;

    @FindBy(xpath = "//dt/span[contains(text(),'Best Way')]")
    private WebElement bestWayOption;

    @FindBy(xpath = "//div[@class='loader']")
    private WebElement loader;

    @FindBy(xpath = "//strong[@id='block-discount-heading']")
    private WebElement discountCodeLink;

    @FindBy(xpath = "//input[@id='coupon_code']")
    private WebElement discountCodeInputField;

    @FindBy(xpath = "//button[contains(@value,'Apply Discount')]")
    private WebElement applyDiscountButton;

    @FindBy(xpath = "//div[contains(@class,'message-error')]")
    private WebElement errorMessage;

    public boolean isExpandedEstimateShippingAndTaxSectionDisplayed() {
        return selenium.isDisplayed(expandedEstimateShippingAndTaxSection);
    }

    public boolean isStateInputFieldDisplayed() {
        return selenium.isDisplayed(stateInputField);
    }

    public boolean isBestWayOptionDisplayed() {
        if(!selenium.isDisplayed(loader)) {
            return selenium.isDisplayed(bestWayOption);
        }
        return false;
    }

    public boolean isDiscountCodeInputFieldDisplayed() {
        return selenium.isDisplayed(discountCodeInputField);
    }

    public String getActualErrorMessage() {
        return selenium.getText(errorMessage);
    }

    public void clickOnEstimateShippingAndTaxText() {
        if(!isExpandedEstimateShippingAndTaxSectionDisplayed()) {
            selenium.click(estimateShippingAndTaxText);
        }
    }

    public void selectFromDropdown() {
        selenium.selectFromDropdown(countryDropdown, "Botswana");
    }

    public void clickOnDiscountCodeLink() {
        if(!isDiscountCodeInputFieldDisplayed()) {
            selenium.click(discountCodeLink);
        }
    }

    public void enterDiscountCode() {
        selenium.type(discountCodeInputField, Constants.INVALID_DISCOUNT_CODE);
    }

    public void clickOnApplyDiscountButton() {
        selenium.click(applyDiscountButton);
    }

}
