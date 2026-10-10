package tests;

import base.ApiDemosBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SlideGestureTest extends ApiDemosBaseTest {

    @Test
    public void shouldSlideSeekBarToTheRight() {
        openViewsItem("Seek Bar");

        WebElement seekBar = waitForVisible(By.id("io.appium.android.apis:id/seek"));
        WebElement progressLabel = driver.findElement(By.id("io.appium.android.apis:id/progress"));
        String progressBeforeSlide = progressLabel.getText();

        Rectangle seekBarArea = seekBar.getRect();
        int startX = seekBarArea.x + seekBarArea.width / 10;
        int endX = seekBarArea.x + seekBarArea.width - seekBarArea.width / 20;
        int centerY = seekBarArea.y + seekBarArea.height / 2;

        // W3C pointer sequence: finger down, slide horizontally, finger up
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence slide = new Sequence(finger, 0)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, centerY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), endX, centerY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(slide));

        // Result: the progress label changed
        Assert.assertNotEquals(progressLabel.getText(), progressBeforeSlide,
                "The seek bar progress should change after sliding");
    }
}
