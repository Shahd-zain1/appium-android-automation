package tests;

import base.ApiDemosBaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class TapGestureTest extends ApiDemosBaseTest {

    @Test
    public void shouldTapViewsEntryAndOpenViewsList() {
        WebElement viewsEntry = waitForVisible(AppiumBy.accessibilityId("Views"));

        // Native tap gesture performed on the element
        driver.executeScript("mobile: clickGesture",
                Map.of("elementId", ((RemoteWebElement) viewsEntry).getId()));

        // Result: the first item of the Views list is now on screen
        Assert.assertTrue(waitForVisible(AppiumBy.accessibilityId("Animation")).isDisplayed(),
                "The Views list should open after the tap");
    }
}
