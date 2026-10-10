package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

/**
 * Shared driver lifecycle for every test class.
 * Each subclass only describes WHICH app (or browser) it wants to automate.
 */
public abstract class BaseTest {

    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
    private static final int EXPLICIT_WAIT_SECONDS = 15;
    private static final Path SCREENSHOT_DIRECTORY = Path.of("build", "screenshots");

    // Device serial from "adb devices". Emulator by default; override with -DdeviceUdid=<serial> for a real phone.
    private static final String DEVICE_UDID = System.getProperty("deviceUdid", "emulator-5554");

    // Optional pause (seconds) before the session closes, handy for taking manual screenshots.
    private static final long PAUSE_BEFORE_QUIT_SECONDS = Long.parseLong(System.getProperty("pauseSeconds", "0"));

    protected AndroidDriver driver;

    /** Subclasses return the capabilities that identify their app or browser. */
    protected abstract UiAutomator2Options buildAppSpecificOptions();

    @BeforeMethod
    public void startDriver() throws Exception {
        UiAutomator2Options options = buildAppSpecificOptions()
                // Target operating system
                .setPlatformName("Android")
                // Appium driver that talks to Android
                .setAutomationName("UiAutomator2")
                // Serial of the emulator or real device to run on
                .setUdid(DEVICE_UDID)
                // Human-readable device label (UDID above does the real targeting)
                .setDeviceName(DEVICE_UDID)
                // Keep the session alive while we inspect the screen
                .setNewCommandTimeout(Duration.ofSeconds(120));

        driver = new AndroidDriver(URI.create(APPIUM_SERVER_URL).toURL(), options);
    }

    @AfterMethod(alwaysRun = true)
    public void stopDriver(ITestResult testResult) throws Exception {
        if (driver == null) {
            return;
        }
        try {
            Thread.sleep(PAUSE_BEFORE_QUIT_SECONDS * 1000);
            saveScreenshot(testResult.getMethod().getMethodName());
        } finally {
            driver.quit();
        }
    }

    protected WebElement waitForVisible(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void saveScreenshot(String testName) throws Exception {
        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        Files.createDirectories(SCREENSHOT_DIRECTORY);
        Files.copy(screenshot.toPath(), SCREENSHOT_DIRECTORY.resolve(testName + ".png"),
                StandardCopyOption.REPLACE_EXISTING);
    }
}
