# Appium Java Cheat Sheet (Android, Appium 3, Java Client 10)

Imports used in the snippets:
```java
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.*;
```

## 1. Desired Capabilities (Options) setup

| Goal | Snippet |
|---|---|
| Create options | `UiAutomator2Options options = new UiAutomator2Options();` |
| Platform | `options.setPlatformName("Android");` |
| Automation engine | `options.setAutomationName("UiAutomator2");` |
| Device name | `options.setDeviceName("Pixel_7");` |
| Target a device by serial | `options.setUdid("emulator-5554");` |
| Built-in app by package | `options.setAppPackage("com.android.settings");` |
| Launch activity | `options.setAppActivity("com.android.settings.Settings");` |
| Wait for any activity | `options.setAppWaitActivity("*");` |
| Install and launch an APK | `options.setApp("C:/appium-project/apps/ApiDemos-debug.apk");` |
| Keep app data between sessions | `options.setNoReset(true);` |
| Session idle timeout | `options.setNewCommandTimeout(Duration.ofSeconds(120));` |
| Any custom capability | `options.setCapability("appium:autoGrantPermissions", true);` |
| Start the driver | `AndroidDriver driver = new AndroidDriver(URI.create("http://127.0.0.1:4723").toURL(), options);` |

Find the package and activity of the open app:
```
adb shell dumpsys window | findstr mCurrentFocus
```

## 2. Element locator strategies

| Strategy | Snippet |
|---|---|
| ID (resource-id) | `driver.findElement(By.id("android:id/checkbox"));` |
| Accessibility ID (content-desc) | `driver.findElement(AppiumBy.accessibilityId("Views"));` |
| XPath | `driver.findElement(By.xpath("//android.widget.TextView[@text='WiFi settings']"));` |
| Class name | `driver.findElements(AppiumBy.className("android.widget.Button"));` |
| UiAutomator (by text) | `driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Internet\")"));` |
| UiAutomator (scroll into view) | `driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Seek Bar\"))"));` |
| CSS selector (web only) | `driver.findElement(By.cssSelector("button[type='submit']"));` |
| Several elements | `List<WebElement> items = driver.findElements(By.className("android.widget.TextView"));` |

Explicit wait:
```java
WebElement element = new WebDriverWait(driver, Duration.ofSeconds(15))
        .until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/edit")));
```

## 3. Common driver and element commands

| Command | Snippet |
|---|---|
| Click | `element.click();` |
| Type text | `element.sendKeys("Shahd");` |
| Clear text | `element.clear();` |
| Read text | `String text = element.getText();` |
| Read an attribute | `String value = element.getAttribute("checked");` |
| Is displayed / enabled | `element.isDisplayed(); element.isEnabled();` |
| Position and size | `Rectangle area = element.getRect();` |
| Screenshot | `File file = driver.getScreenshotAs(OutputType.FILE);` |
| Page source (XML) | `String xml = driver.getPageSource();` |
| Press Back | `driver.navigate().back();` |
| Press a key | `driver.pressKey(new KeyEvent(AndroidKey.HOME));` |
| Launch an app | `driver.activateApp("com.android.settings");` |
| Close an app | `driver.terminateApp("com.android.settings");` |
| Install / remove an APK | `driver.installApp("path/app.apk"); driver.removeApp("io.appium.android.apis");` |
| Current activity | `String activity = driver.currentActivity();` |
| Hide the keyboard | `driver.hideKeyboard();` |
| Orientation | `driver.rotate(ScreenOrientation.LANDSCAPE);` |
| Run an ADB shell command | `driver.executeScript("mobile: shell", Map.of("command", "pm", "args", List.of("list", "packages")));` |
| End the session | `driver.quit();` |

## 4. Gesture automation

| Gesture | Snippet |
|---|---|
| Tap | `driver.executeScript("mobile: clickGesture", Map.of("elementId", ((RemoteWebElement) element).getId()));` |
| Tap by coordinates | `driver.executeScript("mobile: clickGesture", Map.of("x", 300, "y", 800));` |
| Long press | `driver.executeScript("mobile: longClickGesture", Map.of("elementId", id, "duration", 1500));` |
| Double tap | `driver.executeScript("mobile: doubleClickGesture", Map.of("elementId", id));` |
| Scroll (repeat while true) | `boolean more = (Boolean) driver.executeScript("mobile: scrollGesture", Map.of("elementId", listId, "direction", "down", "percent", 1.0));` |
| Swipe | `driver.executeScript("mobile: swipeGesture", Map.of("elementId", id, "direction", "left", "percent", 0.75));` |
| Drag and drop | `driver.executeScript("mobile: dragGesture", Map.of("elementId", id, "endX", 500, "endY", 900));` |
| Pinch open / close | `driver.executeScript("mobile: pinchOpenGesture", Map.of("elementId", id, "percent", 0.75));` |

Slide with the W3C Actions API (finger down, move, finger up):
```java
PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
Sequence slide = new Sequence(finger, 0)
        .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, y))
        .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
        .addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), endX, y))
        .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
driver.perform(List.of(slide));
```

## 5. Web testing configuration (Chrome on Android)

| Goal | Snippet |
|---|---|
| Use Chrome | `options.setCapability("browserName", "Chrome");` |
| Auto-download a matching ChromeDriver | `options.setCapability("appium:chromedriverAutodownload", true);` |
| Chrome arguments | `options.setCapability("appium:chromeOptions", Map.of("args", List.of("--no-first-run")));` |
| Open a URL | `driver.get("https://the-internet.herokuapp.com/login");` |
| Web locators | `driver.findElement(By.id("username")).sendKeys("tomsmith");` |
| Read the page title | `String title = driver.getTitle();` |
| Switch contexts (hybrid apps) | `driver.context("WEBVIEW_chrome");` |
| List contexts | `Set<String> contexts = driver.getContextHandles();` |
| Back to native | `driver.context("NATIVE_APP");` |

Start the server with the ChromeDriver download feature enabled:
```
appium --allow-insecure=uiautomator2:chromedriver_autodownload
```

## 6. Handy server commands

| Goal | Command |
|---|---|
| Start the server | `appium` |
| List devices | `adb devices` |
| Check the setup (Appium 3) | `appium driver doctor uiautomator2` |
| List installed drivers | `appium driver list --installed` |
