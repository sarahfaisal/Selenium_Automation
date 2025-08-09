package testProject.pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected WebElement find(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElement(locator);
    }

    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    // Try multiple locators; return first visible match
    protected WebElement findFlexible(By... locators) {
        for (By l : locators) {
            try {
                List<WebElement> els = driver.findElements(l);
                if (els != null && !els.isEmpty()) {
                    wait.until(ExpectedConditions.visibilityOf(els.get(0)));
                    return els.get(0);
                }
            } catch (Exception ignored) {}
        }
        throw new NoSuchElementException("None of the flexible locators found an element");
    }

    protected List<WebElement> findAllFlexible(By... locators) {
        for (By l : locators) {
            List<WebElement> els = driver.findElements(l);
            if (els != null && !els.isEmpty()) return els;
        }
        return List.of();
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void waitForPageLoad() {
        wait.until(d -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }
}
