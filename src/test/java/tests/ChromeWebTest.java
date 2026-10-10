package tests;

import base.BaseTest;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/** Automates a web page inside Chrome on the Android device. */
public class ChromeWebTest extends BaseTest {

    private static final String LOGIN_PAGE_URL = "https://the-internet.herokuapp.com/login";

    @Override
    protected UiAutomator2Options buildAppSpecificOptions() {
        UiAutomator2Options options = new UiAutomator2Options();
        // Run in the Chrome browser instead of a native app
        options.setCapability("browserName", "Chrome");
        // Let Appium download the ChromeDriver that matches the installed Chrome
        options.setCapability("appium:chromedriverAutodownload", true);
        // Skip Chrome's first-run welcome screens
        options.setCapability("appium:chromeOptions", Map.of("args", List.of("--no-first-run")));
        return options;
    }

    @Test
    public void shouldLogInWithValidCredentialsOnWebPage() {
        driver.get(LOGIN_PAGE_URL);

        // ID locators (web context)
        waitForVisible(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        // CSS locator
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String flashMessage = waitForVisible(By.id("flash")).getText();
        Assert.assertTrue(flashMessage.contains("You logged into a secure area!"),
                "A successful login message should be displayed");
    }
}
