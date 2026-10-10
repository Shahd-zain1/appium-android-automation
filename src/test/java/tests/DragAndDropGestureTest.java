package tests;

import base.ApiDemosBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class DragAndDropGestureTest extends ApiDemosBaseTest {

    @Test
    public void shouldDragFirstDotAndDropItOnThirdDot() {
        openViewsItem("Drag and Drop");

        WebElement sourceDot = waitForVisible(By.id("io.appium.android.apis:id/drag_dot_1"));
        Rectangle targetArea = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_3")).getRect();
        int targetCenterX = targetArea.x + targetArea.width / 2;
        int targetCenterY = targetArea.y + targetArea.height / 2;

        // Native drag gesture: long-press the source, move it, release on the target
               driver.executeScript("mobile: dragGesture", Map.of(
                "elementId", ((RemoteWebElement) sourceDot).getId(),
                "endX", targetCenterX,
                "endY", targetCenterY,
                // Slow drag (pixels per second) so the app registers the drop
                "speed", 400));

        // Result: the app prints a confirmation message
        String resultMessage = waitForVisible(By.id("io.appium.android.apis:id/drag_result_text")).getText();
        Assert.assertEquals(resultMessage, "Dropped!", "The drop result message should be shown");
    }
}
