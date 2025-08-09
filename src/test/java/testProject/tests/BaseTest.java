package testProject.tests;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        // do NOT set webdriver.chrome.driver - Selenium Manager will provide the driver
        driver = new ChromeDriver(options);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
