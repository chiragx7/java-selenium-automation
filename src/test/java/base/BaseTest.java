package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utils.Constants;
import utils.DriverManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected static WebDriver driver;
    protected static Properties properties;

    @BeforeSuite
    public void loadConfig() {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeTest
    public void setUp() {
        if(properties == null) {
            loadConfig();
        }
        if(driver == null) {
            String browser = properties.getProperty("browser");
            DriverManager.initializeDriver(browser);
            driver = DriverManager.getDriver();
            driver.get(Constants.BASE_URL);
        }
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}