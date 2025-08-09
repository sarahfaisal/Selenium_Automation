package testProject.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import testProject.pages.HomePage;
import testProject.pages.ProductPage;
import testProject.pages.SearchResultsPage;

public class SearchAndFilterTest extends BaseTest {

    @Test(priority = 1)
    public void testProductCountGreaterThanZero() {
        driver.get("https://www.daraz.pk/");
        HomePage home = new HomePage(driver);
        SearchResultsPage results = home.search("electronics");
        int count = results.countProducts();
        Assert.assertTrue(count > 0, "Expected products > 0 but found " + count);
    }

    @Test(priority = 2)
    public void testFreeShippingForAProduct() {
        driver.get("https://www.daraz.pk/");
        HomePage home = new HomePage(driver);
        SearchResultsPage results = home.search("electronics");

        // 4. Choose any brand — example: "Samsung". Change as you like.
        results.applyBrandFilter("joyclick");

        // 5. Price range: 500 to 5000 (PKR)
        results.setPriceRange(500, 50000);

        // 6. Ensure there are results
        int count = results.countProducts();
        Assert.assertTrue(count > 0, "No products found after applying brand+price filters");

        // 7. Click product (first product)
        results.clickProductByIndex(0);

        // 8. Check free shipping on product page
        ProductPage productPage = new ProductPage(driver);
        boolean freeShipping = productPage.isFreeShippingAvailable();
        Assert.assertTrue(freeShipping, "Free shipping NOT available for this product (test should fail)");
    }
}