package pageobjects.forgotpassword;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class ForgotPasswordPO extends BasePO {
    public ForgotPasswordPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "email_address")
    private WebElement emailAddressField;

    @FindBy(xpath = "//button/span[text()='Reset My Password']")
    private WebElement resetMyPasswordButton;

    public void enterEmail(String email) {
        selenium.enterEmail(emailAddressField, email);
    }

    public void clickOnResetMyPasswordButton() {
        selenium.click(resetMyPasswordButton);
    }
}
