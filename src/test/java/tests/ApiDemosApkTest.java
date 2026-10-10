package tests;

import base.ApiDemosBaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Installs and launches the ApiDemos APK, then interacts with several elements. */
public class ApiDemosApkTest extends ApiDemosBaseTest {

    private static final String WIFI_NAME = "ShahdHomeWifi";

    @Test
    public void shouldSaveWifiNameInPreferenceDependenciesScreen() {
        // Accessibility ID locator
        waitForVisible(AppiumBy.accessibilityId("Preference")).click();

        // XPath locator
        waitForVisible(By.xpath("//android.widget.TextView[@content-desc='3. Preference dependencies']")).click();

        // ID locator: the checkbox that enables the WiFi settings entry
        waitForVisible(By.id("android:id/checkbox")).click();

        waitForVisible(By.xpath("//android.widget.TextView[@text='WiFi settings']")).click();
        waitForVisible(By.id("android:id/edit")).sendKeys(WIFI_NAME);
        // ID locator: the OK button of the dialog
        driver.findElement(By.id("android:id/button1")).click();

        // Reopen the dialog and check that the saved value is shown
        waitForVisible(By.xpath("//android.widget.TextView[@text='WiFi settings']")).click();
        String savedWifiName = waitForVisible(By.id("android:id/edit")).getText();
        Assert.assertEquals(savedWifiName, WIFI_NAME, "The dialog should show the saved WiFi name");
    }
}
