package testcases;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ExcelUtils;
import utils.SiteConfig;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PracticeAmazon extends BaseTest {

    @BeforeClass
    public void setupReport() {
        test = extent.createTest("Station Suggestion Validation");

    }

    @BeforeMethod
    public void openSite() {
        String dynamicUrl = "https://www.amazon.in/";
        context.homePage.goTo(dynamicUrl);
    }

    @Test
    public void ValidateAmazon() throws InterruptedException {
        WebElement SearchButton = driver.findElement(SiteConfig.getAmazonLocator("searchBox"));
        SearchButton.sendKeys("iphone");
        Thread.sleep(2000);

        // Get all suggestions
        List<WebElement> suggestions = driver.findElements(SiteConfig.getAmazonLocator("suggestionItems"));

        // Print all suggestions in console
        System.out.println("Search Suggestions:");
        for (WebElement suggestion : suggestions) {
            System.out.println(suggestion.getText());
        }

        // Click the first suggestion if available
        if (!suggestions.isEmpty()) {
            suggestions.get(0).click();
        } else {
            System.out.println("No suggestions found.");
        }

    }

}