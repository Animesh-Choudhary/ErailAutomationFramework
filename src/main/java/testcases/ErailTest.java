package testcases;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ExcelUtils;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ErailTest extends BaseTest {

    @BeforeClass
    public void setupReport() {
        test = extent.createTest("Station Suggestion Validation");

    }
    @BeforeMethod
    public void openSite() {
        String dynamicUrl = "https://erail.in/";
        context.homePage.goTo(dynamicUrl);
    }

    @Test
    public void validateSpecificStationSuggestion() throws Exception {
        test = extent.createTest("Step X: Validate & Select Specific Dropdown Suggestion");

        int suggestionIndex = 3; // 4th item (index starts at 0)

        context.homePage.clearAndTypeFrom("DEL");
        // WebDriverWait to wait for dropdown to be populated
        WebDriverWait wait = new WebDriverWait(context.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(context.homePage.suggestionList, suggestionIndex));


        String selectedSuggestion = context.homePage.printAndSelectSpecificDropdownSuggestion(suggestionIndex);

        test.info(" 4th Station Suggestion Selected: " + selectedSuggestion);

        if (!selectedSuggestion.equalsIgnoreCase("Invalid index")) {
            test.pass(" Successfully selected the 4th dropdown suggestion: " + selectedSuggestion);
        } else {
            test.fail(" Failed to select the 4th dropdown suggestion. Index may be out of range.");
            Assert.fail("Dropdown suggestion index out of bounds.");
        }
    }



    @Test
    public void testSelectDate() {
        test = extent.createTest("Station Suggestion + Date Picker Validation");
        String baseDate = "20-May-2025";
        boolean success = context.homePage.selectDate30DaysAfter(baseDate);

        Assert.assertTrue(success, "Date not selected correctly in calendar.");
    }
    @Test
    public void compareActualDropdownWithExpectedExcelData() throws Exception {
        test = extent.createTest("Step 7: Compare Dropdown Stations with Expected Excel Data");

        String expectedFile = "expectedStations.xlsx";
        String actualOutputFile = "src/main/java/Output/actual_stations.xlsx";

        // Step 1: Type to trigger dropdown
        context.homePage.clearAndTypeFrom("DEL");

        // Step 2: Capture actual dropdown stations
        List<String> actualStations = context.homePage.getDropdownSuggestions();

        // Step 3: Save actual stations to Excel
        ExcelUtils.writeActualStations(actualOutputFile, actualStations);

        // Step 4: Read expected stations from Excel
        List<String> expectedStations = ExcelUtils.readExpectedStations(expectedFile);

        // Step 5: Compare
        Set<String> expectedSet = new HashSet<>(expectedStations);
        Set<String> actualSet = new HashSet<>(actualStations);

        Set<String> missingStations = new HashSet<>(expectedSet);     // In expected but missing from actual
        missingStations.removeAll(actualSet);

        Set<String> unexpectedStations = new HashSet<>(actualSet);    // In actual but not expected
        unexpectedStations.removeAll(expectedSet);

        // Log expected
        System.out.println("✅ Expected Stations from Excel:\n" + expectedStations);
        test.info("✅ Expected Stations from Excel:\n" + expectedStations);

        if (missingStations.isEmpty() && unexpectedStations.isEmpty()) {
            test.pass("✅ All expected stations matched with dropdown.");
            Assert.assertTrue(true, "✅ All expected stations matched with dropdown.");
        } else {
            if (!missingStations.isEmpty()) {
                System.out.println("❌ Missing in dropdown (present in Excel, but not in UI):\n" + missingStations);
                test.fail("❌ Missing in dropdown (present in Excel, but not in UI):\n" + missingStations);
            }
            if (!unexpectedStations.isEmpty()) {
                System.out.println("❌ Extra in dropdown (present in UI, but not in Excel):\n" + unexpectedStations);
                test.fail("❌ Extra in dropdown (present in UI, but not in Excel):\n" + unexpectedStations);
            }
            Assert.fail("❌ Station mismatch found.");
        }
    }


}