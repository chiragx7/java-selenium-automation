package pageobjects.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;
import java.util.List;
import java.util.Random;

public class HomePO extends BasePO {
    public HomePO(WebDriver driver) {
        super(driver);
    }

    private List<String> colors;
    private List<String> sizes;
    private final Random random = new Random();

    @FindBy(xpath = "//h2[text()='Hot Sellers']")
    private WebElement hotSellersSection;

    @FindBy(xpath = "//div[contains(@class,'products-grid')]")
    private WebElement productsGrid;

    @FindBy(xpath = "//div[contains(@class,'products-grid')]/ol/li[2]//strong/a")
    private WebElement product;

    @FindBy(xpath = "//div[contains(@class,'message-success')]/div")
    private WebElement successMessage;

    @FindBy(xpath = "//span[@class='counter-number']")
    private WebElement quantityCount;

    @FindBy(xpath = "//a[contains(@class,'showcart')]")
    private WebElement cartIcon;

    @FindBy(xpath = "//a[contains(@class,'viewcart')]")
    private WebElement viewAndEditCartLink;

    @FindBy(xpath = "//a[contains(@class,'active')]")
    private WebElement cartIconActive;

    @FindBy(xpath = "//h1[@class='page-title']/span")
    private WebElement pageTitle;

    @FindBy(xpath = "//strong[contains(@class, 'empty')]")
    private WebElement emptyCartText;

    @FindBy(xpath = "//button[@id='top-cart-btn-checkout']")
    private WebElement proceedToCheckoutButton;

    @FindBy(xpath = "//a[@title='Remove item']")
    private WebElement cartDeleteIcon;

    @FindBy(xpath = "//footer/button[contains(@class,'action-accept')]")
    private WebElement dialogOkButton;

    @FindBy(xpath = "//input[contains(@class,'cart-item-qty')]")
    private WebElement cartQuantityInputField;

    @FindBy(xpath = "//button[@class='update-cart-item']")
    private WebElement cartUpdateButton;

    @FindBy(xpath = "//div[@id='modal-content-54']")
    private WebElement maximumQuantityMessage;

    @FindBy(xpath = "//span[contains(text(),'See Details')]")
    private WebElement seeDetailsText;

    String randomColorXpath = "//a[@title='%s']/parent::*/parent::div//div[contains(@class,'swatch-option color')]";
    String randomSizeXpath = "//a[@title='%s']/parent::*/parent::div//div[contains(@class,'swatch-option text')]";
    String addToCartButtonXpath = "//a[@title='%s']/parent::*/parent::div//button[@title='Add to Cart']";
    String productInCartXpath = "//a[text()='%s']/ancestor::div[@class='product-item-details']";
    String addToWishListIconXpath = "//a[@title='%s']/parent::strong/following-sibling::div//a[@title='Add to Wish List']";
    String cartProductSizeAndColorXpath = "//dt[text()='%s']/following-sibling::dd[position()=1]";

    public WebElement getAddToWishListIcon(String productName) {
        return driver.findElement(By.xpath(String.format(addToWishListIconXpath, productName)));
    }

    public List<WebElement> getColorElements(String productName) {
        return driver.findElements(By.xpath(String.format(randomColorXpath, productName)));
    }

    public List<WebElement> getSizeElements(String productName) {
        return driver.findElements(By.xpath(String.format(randomSizeXpath, productName)));
    }

    public WebElement getAddToCartButton(String productName) {
        return selenium.waitForElementToBeClickable(By.xpath(String.format(addToCartButtonXpath, productName)));
    }

    public String getProductName() {
        return selenium.getText(product);
    }

    public String getSizeOrColorOfTheProductAddedInCart(String value) {
        return selenium.getText(driver.findElement(By.xpath(String.format(cartProductSizeAndColorXpath, value))));
    }

    public String getEveryPageTitle() {
        return selenium.getText(pageTitle);
    }

    public String getActualSuccessMessage() {
        return selenium.getText(successMessage);
    }

    public String getMaximumQuantityMessage() {
        return selenium.getText(maximumQuantityMessage);
    }

    public boolean isProductAddedToCartDisplayed(String productName) {
        return selenium.isDisplayed(driver.findElement(By.xpath(String.format(productInCartXpath, productName))));
    }

    public boolean isCartEmpty() {
        return selenium.isDisplayed(emptyCartText);
    }

    public boolean isCartActive() {
        return selenium.isDisplayed(cartIconActive);
    }

    public boolean isHotSellersSectionDisplayed() {
        return selenium.isDisplayed(hotSellersSection);
    }

    public boolean isDialogDisplayed() {
        return selenium.isDisplayed(dialogOkButton);
    }

    public boolean isCartUpdateButtonDisplayed() {
        return selenium.isDisplayed(cartUpdateButton);
    }

    public boolean isWishListIconVisible(String productName) {
        return selenium.isDisplayed(getAddToWishListIcon(productName));
    }

    public void clickOnEveryNavigationMenuItemsByLinkText(String itemName) {
        selenium.click(driver.findElement(By.linkText(itemName)));
    }

    public String selectRandomColor(String productName) {
        List<WebElement> colorElements = getColorElements(productName);

        if (colorElements.isEmpty()) {
            System.out.println("No color options found for product: " + productName);
        }

        int numOfColors = colorElements.size();
        int randomIndex = random.nextInt(numOfColors);

        colorElements.get(randomIndex).click();
        return selenium.getAttribute(colorElements.get(randomIndex),"option-label");
    }

    public String selectRandomSize(String productName) {
        List<WebElement> sizeElements = getSizeElements(productName);

        if (sizeElements.isEmpty()) {
            System.out.println("No size options found for product: " + productName);
        }

        int numOfSizes = sizeElements.size();
        int randomIndex = random.nextInt(numOfSizes);

        sizeElements.get(randomIndex).click();
        return selenium.getAttribute(sizeElements.get(randomIndex),"option-label");
    }

    public void clickOnCartUpdateButton() {
        selenium.click(cartUpdateButton);
    }

    public void scrollToHotSellersSection() {
        selenium.getActions().scrollToElement(productsGrid).perform();
    }

    public void clickOnAddToCartButton(String productName) {
        selenium.click(getAddToCartButton(productName));
    }

    public void clickOnCartIcon() {
        if (selenium.isDisplayed(quantityCount)) {
            selenium.click(cartIcon);
        } else {
            return;
        }
    }

    public void clickOnViewAndEditCartLink() {
        selenium.click(viewAndEditCartLink);
    }

    public void clickOnCartIconIfCartIsNotActive() {
        if (!isCartActive()) {
            selenium.click(cartIcon);
        }
    }

    public void clickOnDeleteIconInCart() {
        selenium.click(cartDeleteIcon);
    }

    public void clickOnProceedToCheckoutButton() {
        selenium.click(proceedToCheckoutButton);
    }

    public void clickOnDialogOkButton() {
        if (isDialogDisplayed()) {
            selenium.click(dialogOkButton);
        }
    }

    public void enterQuantityInQuantityField(String quantity) {
        selenium.type(cartQuantityInputField, quantity);
    }

    public void hoverOnParticularProduct() {
        selenium.getActions().moveToElement(product).perform();
    }

    public void clickOnAddToWishListIcon() {
        selenium.click(getAddToWishListIcon(getProductName()));
    }

    public void clickOnSeeDetailsText() {
        selenium.click(seeDetailsText);
    }

    // Comments moved here:
    // @FindBy(xpath = "//div[contains(@class,'products-grid')]//li[1]//a[@title='Add to Wish List']")
    // private WebElement addToWishListIcon;

    // public WebElement getProductImage() {
    //     return driver.findElement(By.xpath(String.format(productImageXpath, getProductName())));
    // }

    // public WebElement getProductSize(String productSize) {
    //     return driver.findElement(By.xpath(String.format(productSizeXpath, getProductName(), productSize)));
    // }

    // public WebElement getProductColor(String productColor) {
    //     return driver.findElement(By.xpath(String.format(productColorXpath, getProductName(), productColor)));
    // }

    // public WebElement getCartProductNameInCart() {
    //     return selenium.waitForElementToBeVisible(By.xpath(String.format(productInCartXpath, getProductName())));
    // }

    // public boolean isSizeAvailable(String size, String productName) {
    //     try {
    //         return driver.findElement(By.xpath(String.format(productSizeXpath, productName, size))).isDisplayed();
    //     } catch (NoSuchElementException e) {
    //         return false;
    //     }
    // }

    // public boolean isColorAvailable(String color, String productName) {
    //     try {
    //         return driver.findElement(By.xpath(String.format(productColorXpath, productName, color))).isDisplayed();
    //     } catch (NoSuchElementException e) {
    //         return false;
    //     }
    // }

    // public void selectProductSize(String productName) {
    //     try {
    //         ObjectMapper mapper = new ObjectMapper();
    //         JsonNode node = mapper.readTree(new File("src/main/resources/sizesandcolors.json"));
    //         sizes = new ArrayList<>();
    //         node.get("availableSizes").forEach(size -> sizes.add(size.asText()));
    //         String preferredSize = node.get("preferredSize").asText();

    //         selenium.getActions().moveToElement(getProductImage()).perform();

    //         // For selecting dynamic product size
    //         if (isSizeAvailable(preferredSize, productName)) {
    //             getProductSize(preferredSize).click();
    //             return;
    //         }

    //         for (String size : sizes) {
    //             if (isSizeAvailable(size, productName)) {
    //                 getProductSize(size).click();
    //                 return;
    //             }
    //         }

    //         throw new RuntimeException("No such size found for " + getProductName());
    //     } catch (Exception e) {
    //         System.out.println(e.getMessage());
    //     }
    // }

    // public void selectProductColor(String productName) {
    //     try {
    //         ObjectMapper mapper = new ObjectMapper();
    //         JsonNode node = mapper.readTree(new File("src/main/resources/sizesandcolors.json"));
    //         colors = new ArrayList<>();
    //         node.get("availableColors").forEach(color -> colors.add(color.asText()));
    //         String preferredColor = node.get("preferredColor").asText();

    //         // For selecting dynamic product color
    //         if (isColorAvailable(preferredColor, productName)) {
    //             getProductColor(preferredColor).click();
    //             return;
    //         }

    //         for (String color : colors) {
    //             if (isColorAvailable(color, productName)) {
    //                 getProductColor(color).click();
    //                 return;
    //             }
    //         }

    //         throw new RuntimeException("No such color found for " + getProductName());
    //     } catch (Exception e) {
    //         System.out.println(e.getMessage());
    //     }
    // }
}
