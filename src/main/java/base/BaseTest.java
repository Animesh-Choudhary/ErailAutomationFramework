package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import context.TestContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class BaseTest {
    protected WebDriver driver;
    protected TestContext context;
    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeSuite
    public void setUpReport() throws IOException {
        String reportPath = "Test-output/ERailReport.html";

        // Delete old report
        File oldReport = new File(reportPath);
        if (oldReport.exists()) {
            boolean deleted = oldReport.delete();
            System.out.println(deleted ? "✅ Old report deleted." : "⚠️ Could not delete old report.");
        }

        // Setup Extent Report
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.loadJSONConfig(new File("src/main/resources/extent-config.json"));

        extent = new ExtentReports(); // ✅ only once
        extent.attachReporter(spark);
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        context = new TestContext(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
