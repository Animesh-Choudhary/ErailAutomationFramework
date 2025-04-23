package context;

import org.openqa.selenium.WebDriver;
import pages.ErailHomePage;

public class TestContext {
    public WebDriver driver;
    public ErailHomePage homePage;

    public TestContext(WebDriver driver) {
        this.driver = driver;
        homePage = new ErailHomePage(driver);
    }
}