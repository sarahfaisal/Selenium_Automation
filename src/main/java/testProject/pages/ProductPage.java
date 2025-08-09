package testProject.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage extends BasePage {
    public ProductPage(WebDriver driver) {
        super(driver);
        waitForPageLoad();
    }

    // 8. Detect free shipping — look for page text "Free Shipping" (case-insensitive)
    public boolean isFreeShippingAvailable() {
        try {
            List<WebElement> els = driver.findElements(By.xpath(
                    "//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'free shipping') or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'free delivery')]"
            ));
            return els != null && !els.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}