// src/main/java/testProject/pages/HomePage.java
package testProject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
    private By searchInput = By.cssSelector("input[type='search'], input[name='q'], input[id*='search']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // perform search and return results page
    public SearchResultsPage search(String query) {
        WebElement input = findFlexible(searchInput);
        input.clear();
        input.sendKeys(query);
        input.sendKeys(Keys.ENTER);
        return new SearchResultsPage(driver);
    }
}
