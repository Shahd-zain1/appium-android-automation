package base;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.nio.file.Path;

/** Shared setup for every test that runs against the ApiDemos APK. */
public abstract class ApiDemosBaseTest extends BaseTest {

    // Default: apps/ApiDemos-debug.apk inside the project. Override with -DapkPath=<full path>.
    private static final String APK_PATH = System.getProperty("apkPath",
            Path.of("apps", "ApiDemos-debug.apk").toAbsolutePath().toString());

    @Override
    protected UiAutomator2Options buildAppSpecificOptions() {
        // "app" makes Appium install the APK and launch its main activity automatically
        return new UiAutomator2Options().setApp(APK_PATH);
    }

    protected void openViewsScreen() {
        // Accessibility ID locator: matches the element's content-desc
        waitForVisible(AppiumBy.accessibilityId("Views")).click();
    }

    /** Opens the Views screen, scrolls the list until the item is visible, then taps it. */
    protected void openViewsItem(String itemName) {
        openViewsScreen();
        // UiAutomator locator: scrolls the list natively until the text is on screen
        String scrollToItem = "new UiScrollable(new UiSelector().scrollable(true).instance(0))"
                + ".scrollIntoView(new UiSelector().text(\"" + itemName + "\"))";
        driver.findElement(AppiumBy.androidUIAutomator(scrollToItem)).click();
    }
}
