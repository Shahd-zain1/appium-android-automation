package tests;

import base.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Automates the built-in Android Settings app. */
public class BuiltInSettingsAppTest extends BaseTest {

    @Override
    protected UiAutomator2Options buildAppSpecificOptions() {
        return new UiAutomator2Options()
                // Package of the built-in Settings app
                .setAppPackage("com.android.settings")
                // Activity that Appium launches
                .setAppActivity("com.android.settings.Settings")
                // Accept whatever activity Settings lands on after launch
                .setAppWaitActivity("*");
    }

    @Test
    public void shouldOpenNetworkAndInternetScreenFromSettingsHome() {
        // XPath locator: finds the menu entry by its visible text
        By networkEntry = By.xpath("//android.widget.TextView[@text='Network & internet']");
        waitForVisible(networkEntry).click();

        // UiAutomator locator: finds an element on the next screen by its text
        By internetEntry = AppiumBy.androidUIAutomator("new UiSelector().text(\"Internet\")");
        Assert.assertTrue(waitForVisible(internetEntry).isDisplayed(),
                "The 'Internet' entry should be visible on the Network & internet screen");
    }
}
