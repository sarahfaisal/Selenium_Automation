package testProject.pages;

import org.openqa.selenium.*;
import java.util.List;

public class SearchResultsPage extends BasePage {
    public SearchResultsPage(WebDriver driver) {
        super(driver);
        waitForPageLoad();
        closePopupsIfAny();
    }

    private void closePopupsIfAny() {
        try {
            List<By> closes = List.of(
                    By.cssSelector("button[aria-label='Close']"),
                    By.cssSelector(".close"),
                    By.cssSelector("button[data-qa='close-btn']")
            );
            for (By c : closes) {
                List<WebElement> els = driver.findElements(c);
                for (WebElement e : els) {
                    try { if (e.isDisplayed()) e.click(); } catch (Exception ignore) {}
                }
            }
        } catch (Exception ignored) {}
    }

    // 4. Click Brand checkbox (robust tries by label text)
    public void applyBrandFilter(String brandName) {
        String brandLower = brandName.toLowerCase();
        try {
            By labelXpath = By.xpath("//label[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + brandLower + "')]");
            WebElement label = findFlexible(labelXpath, By.xpath("//span[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + brandLower + "')]"));
            try {
                label.click(); // clicking label usually toggles checkbox
            } catch (Exception e) {
                // try clicking checkbox inside label
                WebElement input = label.findElement(By.xpath(".//input"));
                if (!input.isSelected()) input.click();
            }
            waitForPageLoad();
        } catch (Exception e) {
            throw new RuntimeException("Brand filter '" + brandName + "' not found or not clickable. Inspect and adjust selector.", e);
        }
    }

    // 5. Set price range and apply
    public void setPriceRange(int min, int max) {
        try {
            By minSelector = By.cssSelector("input[placeholder='Min'], input[placeholder='min'], input[name*='min']");
            By maxSelector = By.cssSelector("input[placeholder='Max'], input[placeholder='max'], input[name*='max']");
            WebElement minEl = findFlexible(minSelector, By.xpath("//input[contains(@class,'min') or contains(@id,'min') or contains(@name,'min')]"));
            WebElement maxEl = findFlexible(maxSelector, By.xpath("//input[contains(@class,'max') or contains(@id,'max') or contains(@name,'max')]"));

            minEl.clear();
            minEl.sendKeys(String.valueOf(min));

            maxEl.clear();
            maxEl.sendKeys(String.valueOf(max));

            // try click apply button if present
            List<WebElement> applyButtons = driver.findElements(By.xpath("//button[contains(., 'Apply') or contains(., 'OK') or contains(., 'Apply Filter')]"));
            if (!applyButtons.isEmpty()) {
                applyButtons.get(0).click();
            } else {
                maxEl.sendKeys(Keys.ENTER);
            }
            waitForPageLoad();
        } catch (Exception e) {
            throw new RuntimeException("Unable to set price range - selectors likely need adjustment", e);
        }
    }

    // 6. Count number of products visible in results
    public int countProducts() {
        // try multiple common selectors; return size if >0
        List<By> guesses = List.of(
                By.cssSelector("div[data-qa-locator='product-item']"),
                By.cssSelector("div.sku-item"),
                By.cssSelector("div[data-sqe='item']"),
                By.xpath("//a[contains(@href,'/product') or contains(@href,'/item')]"),
                By.cssSelector("div.c2prKC"), // example of possible class (site-specific)
                By.cssSelector("div.box--item")
        );
        for (By s : guesses) {
            List<WebElement> found = driver.findElements(s);
            if (found != null && found.size() > 0) return found.size();
        }
        // last-resort: search for many anchor tags inside results region
        List<WebElement> anchors = driver.findElements(By.xpath("//div[contains(@id,'module_product') or contains(@class,'list')]/descendant::a"));
        if (anchors != null && anchors.size() > 0) return anchors.size();
        return 0;
    }

    // 7. Click on any one product (by index)
    public void clickProductByIndex(int index) {
        List<By> possible = List.of(
                By.cssSelector("div[data-qa-locator='product-item'] a[href]"),
                By.cssSelector("div.sku-item a[href]"),
                By.cssSelector("div[data-sqe='item'] a[href]"),
                By.xpath("//a[contains(@href,'/product') or contains(@href,'/item')]")
        );
        List<WebElement> items = List.of();
        for (By p : possible) {
            items = driver.findElements(p);
            if (items != null && items.size() > 0) break;
        }
        if (items == null || items.size() == 0 || index >= items.size()) {
            throw new RuntimeException("No products found to click (inspect selectors).");
        }
        WebElement chosen = items.get(index);
        // click and switch to new window if opened
        chosen.click();
        // switch to the last (new) window if a new tab opened
        for (String win : driver.getWindowHandles()) {
            driver.switchTo().window(win);
        }
        waitForPageLoad();
    }
}
