package testProject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class main {
    public static void main(String[] args) {
//        System.setProperty("webdriver.chrome.driver", "D:\\downloads\\chrome-win32\\chrome.exe");
        WebDriver driver=new ChromeDriver();
        driver.get("https://www.google.com");
    }
}
