package tests;

import base.ApiDemosBaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class ScrollGestureTest extends ApiDemosBaseTest {

    @Test
    public void shouldScrollViewsListToTheBottom() {
        openViewsScreen();
        String listId = ((RemoteWebElement) waitForVisible(By.id("android:id/list"))).getId();

        // The gesture returns true while there is still more content to scroll
        boolean canScrollMore;
        do {
            canScrollMore = (Boolean) driver.executeScript("mobile: scrollGesture",
                    Map.of("elementId", listId, "direction", "down", "percent", 1.0));
        } while (canScrollMore);

        // Result: the first item scrolled out of view
        Assert.assertTrue(driver.findElements(AppiumBy.accessibilityId("Animation")).isEmpty(),
                "The top item should no longer be visible after scrolling to the bottom");
    }
}
