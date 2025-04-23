package utils;

import org.openqa.selenium.By;
import java.util.HashMap;
import java.util.Map;

public class SiteConfig {
    private static final Map<String, By> locators = new HashMap<>();
    // Amazon locators
    private static final Map<String, By> amazonLocators = new HashMap<>();

    static {
        locators.put("fromInput", By.id("txtStationFrom"));
        locators.put("suggestionList", By.xpath("//div[@class='autocomplete']//div[@title]"));
        //calender xpath
        locators.put("calendarInput", By.xpath("//input[@title='Select Departure date for availability']"));
        locators.put("calendarMonthBlocks", By.xpath("//table[@class='WithBorder']"));
        locators.put("calendarHeader", By.xpath(".//td[@colspan='7']"));
    }
    static {
        // Amazon locators
        amazonLocators.put("searchBox", By.id("twotabsearchtextbox"));
        amazonLocators.put("suggestionBox", By.id("nav-flyout-searchAjax"));
        amazonLocators.put("suggestionItems", By.xpath("//div[@id='nav-flyout-searchAjax']//div[contains(@class, 's-suggestion')]"));
    }

    public static By getLocator(String key) {
        return locators.get(key);
    }

    // Generic method to get Amazon locators
    public static By getAmazonLocator(String key) {
        return amazonLocators.get(key);
    }
}