package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import lombok.extern.slf4j.Slf4j; // For logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Hook {

    private static WebDriver driver;
    private static final Logger log = LoggerFactory.getLogger(Hook.class);
    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        log.info("Hooks: WebDriver initialized.");
    }

    @After
    public void tearDown(Scenario scenario) {
        log.info("Hook: Tearing down WebDriver for scenario: '{}'. Status: {}", scenario.getName(), scenario.getStatus());
        // Take screenshot if scenario fails
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot on Failure");
                log.info("Hook: Screenshot taken for failed scenario.");
            } catch (Exception e) {
                log.error("Hook: Failed to take screenshot: {}", e.getMessage());
            }
        }
        // Quit the WebDriver
        if (driver != null) {
            driver.quit();
        }

    }

    public static WebDriver getDriver() {
        return driver;
    }
}
