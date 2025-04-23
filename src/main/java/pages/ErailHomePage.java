package pages;

import org.openqa.selenium.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.SiteConfig;

public class ErailHomePage {
    private WebDriver driver;
    private By fromInput;
    public By suggestionList;
    private By calendarInput;
    private By calendarMonthBlocks;
    private By calendarHeader;

    public ErailHomePage(WebDriver driver) {
        this.driver = driver;
        this.fromInput = SiteConfig.getLocator("fromInput");
        this.suggestionList = SiteConfig.getLocator("suggestionList");
        this.calendarInput = SiteConfig.getLocator("calendarInput");
        this.calendarMonthBlocks = SiteConfig.getLocator("calendarMonthBlocks");
        this.calendarHeader = SiteConfig.getLocator("calendarHeader");
    }

    public void goTo(String url) {
        driver.get(url);
    }

    public void clearAndTypeFrom(String text) {
        WebElement input = driver.findElement(fromInput);
        input.clear();
        input.sendKeys(text);
    }

    public String printAndSelectSpecificDropdownSuggestion(int index) throws InterruptedException {
        List<WebElement> suggestions = driver.findElements(suggestionList);

        System.out.println("🔍 Total suggestions found: " + suggestions.size());

        if (index < suggestions.size()) {
            WebElement element = suggestions.get(index);

            // Get the station name
            String stationName = element.getText().trim();
            System.out.println("✅ Selected position station name in the dropdown: " + stationName);

            // Click the element to select it
            element.click();
            Thread.sleep(3000);
            System.out.println("🖱️ Clicked on the station: " + stationName);

            return stationName;
        } else {
            System.out.println("❌ Index out of bounds for dropdown suggestions.");
            return "Invalid index";
        }
    }




    public List<String>     getDropdownSuggestions() {
        List<WebElement> suggestions = driver.findElements(suggestionList);
        List<String> values = new ArrayList<>();
        for (WebElement el : suggestions) {
            values.add(el.getText());
        }
        return values;
    }

    public boolean selectDate30DaysAfter(String inputDateStr) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
            LocalDate inputDate = LocalDate.parse(inputDateStr, inputFormatter);
            LocalDate targetDate = inputDate.plusDays(30);

            String targetDay = String.valueOf(targetDate.getDayOfMonth());
            String targetMonth = targetDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            String targetYear = String.valueOf(targetDate.getYear());

            WebElement calendarInputElement = driver.findElement(calendarInput);
            calendarInputElement.click();
            Thread.sleep(500);

            List<WebElement> monthTables = driver.findElements(calendarMonthBlocks);
            boolean dateClicked = false;

            for (WebElement table : monthTables) {
                WebElement header = table.findElement(calendarHeader);
                String headerText = header.getText();
                String[] parts = headerText.split("-");
                String monthInHeader = parts[0].trim();
                String yearInHeader = "20" + parts[1].trim();

                if (monthInHeader.equalsIgnoreCase(targetMonth) && yearInHeader.equals(targetYear)) {
                    try {
                        WebElement dayCell = table.findElement(By.xpath(".//td[normalize-space(text())='" + targetDay + "']"));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dayCell);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dayCell);
                        dateClicked = true;
                    } catch (Exception e) {
                        System.out.println(" Day not found, fallback activated.");
                    }
                    break;
                }
            }

            // Fallback to direct input if needed
            if (!dateClicked) {
                String fallbackDate = targetDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy E", Locale.ENGLISH));
                ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + fallbackDate + "';", calendarInputElement);
                calendarInputElement.sendKeys(Keys.TAB);
            }

            Thread.sleep(1000);
            WebElement refreshedInput = driver.findElement(calendarInput); // 💡 Refetch to avoid stale
            String actualDate = refreshedInput.getAttribute("value").trim();
            String expectedDate = targetDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy E", Locale.ENGLISH));

            // 👇👇 Added log here
            System.out.println("📅 Calendar shows date: " + actualDate);
            System.out.println("✅ Expected date after +30 days: " + expectedDate);

            return actualDate.equals(expectedDate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
