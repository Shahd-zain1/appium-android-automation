# Appium Android Automation (Java + Gradle + TestNG)

**Repository:** https://github.com/Shahd-zain1/appium-android-automation
**Author:** Shahd Zain ([@Shahd-zain1](https://github.com/Shahd-zain1))

Mobile automation tests written with **Appium 3**, **UiAutomator2**, and the **Appium Java Client**.
The project automates the built-in Android Settings app, the ApiDemos APK, the four core mobile gestures
(Tap, Scroll, Slide, Drag & Drop), and a web page in Chrome on Android.

## Tested environment

| Tool | Version |
|---|---|
| OS | Windows 10 |
| Node.js / npm | 24.21.0 / 11.19.0 |
| Appium Server | 3.8.0 |
| Driver | uiautomator2 8.7.0 |
| JDK | 21 (Android Studio JBR) |
| Appium Java Client | 10.1.1 |
| Emulator | Pixel 7, API 36, Google Play |
| Real device | Samsung Galaxy A36 5G, Android 16 |

## Project structure

```
src/test/java/
  base/
    BaseTest.java              shared driver start/stop, explicit waits, auto screenshot
    ApiDemosBaseTest.java      shared APK capability and navigation helpers
  tests/
    BuiltInSettingsAppTest.java    built-in app (Settings)
    ApiDemosApkTest.java           APK install + several element interactions
    TapGestureTest.java
    ScrollGestureTest.java
    SlideGestureTest.java
    DragAndDropGestureTest.java
    ChromeWebTest.java             web test in Chrome
apps/ApiDemos-debug.apk            the APK under test (place it here)
docs/Appium-Java-Cheat-Sheet.md    the cheat sheet
```

## 1. Environment setup (Windows)

1. **Node.js** (LTS 20.19+ / 22.12+ / 24): https://nodejs.org. Check with `node -v` and `npm -v`.
2. **Android Studio**: install it, then open *SDK Manager* and install:
   - an Android SDK Platform (API 35 or 36)
   - SDK Tools: Build-Tools, Command-line Tools (latest), Platform-Tools, Android Emulator
3. **JDK**: this project uses the JDK bundled with Android Studio (`...\Android Studio\jbr`). Any JDK 17+ works.
4. **Environment variables** (User variables):

   | Name | Value |
   |---|---|
   | `ANDROID_HOME` | `C:\Users\<you>\AppData\Local\Android\Sdk` |
   | `JAVA_HOME` | `C:\Program Files\Android\Android Studio\jbr` |

   Add these entries to `Path`:
   ```
   %JAVA_HOME%\bin
   %ANDROID_HOME%\platform-tools
   %ANDROID_HOME%\emulator
   %ANDROID_HOME%\cmdline-tools\latest\bin
   ```
   Open a **new** CMD window and verify: `java -version`, `adb version`, `emulator -version`.
5. **Appium and the driver**:
   ```
   npm install -g appium
   appium driver install uiautomator2
   appium driver list --installed
   ```
6. **Appium Inspector**: download the Windows installer from https://github.com/appium/appium-inspector/releases.
7. **AVD emulator**: Android Studio > Device Manager > Create Device (Pixel 7, a *Google Play* image so Chrome exists).
8. **Gradle**: `choco install gradle` (or install from https://gradle.org/install). Check with `gradle -v`.
9. **Verify the setup**:
   ```
   appium driver doctor uiautomator2
   ```
   > `appium-doctor` v1 is deprecated and targets Appium 1/2. For Appium 3 the official equivalent is
   > `appium driver doctor uiautomator2`, which reports **0 required fixes**. The remaining warnings
   > (`bundletool`, `ffmpeg`, `gst-launch`) are optional features that these tests do not use.

## 2. Run the tests

1. Start the emulator: `emulator -avd Pixel_7` and confirm `adb devices` lists `emulator-5554  device`.
2. Copy `ApiDemos-debug.apk` into the `apps/` folder.
3. Start the Appium server in its own terminal and leave it running. The flag lets Appium download the
   ChromeDriver that matches Chrome (needed only for the web test):
   ```
   appium --allow-insecure=uiautomator2:chromedriver_autodownload
   ```
4. Run the tests from the project folder:

   | Goal | Command |
   |---|---|
   | Everything | `gradle test` |
   | Built-in app | `gradle test --tests "tests.BuiltInSettingsAppTest"` |
   | APK | `gradle test --tests "tests.ApiDemosApkTest"` |
   | Tap | `gradle test --tests "tests.TapGestureTest"` |
   | Scroll | `gradle test --tests "tests.ScrollGestureTest"` |
   | Slide | `gradle test --tests "tests.SlideGestureTest"` |
   | Drag & Drop | `gradle test --tests "tests.DragAndDropGestureTest"` |
   | Chrome web test | `gradle test --tests "tests.ChromeWebTest"` |

Optional flags (add to any command above):

| Flag | Purpose |
|---|---|
| `-DdeviceUdid=<serial>` | run on a specific device (serial from `adb devices`) |
| `-DapkPath=<full path>` | use an APK stored somewhere else |
| `-DpauseSeconds=5` | keep the screen open for a few seconds before the session closes (for screenshots) |

After each test, a screenshot of the device screen is saved in `build/screenshots/`.

## 3. Run on a real device (Samsung Galaxy A36)

1. Settings > About phone > Software information > tap **Build number** 7 times.
2. Settings > Developer options > turn on **USB debugging**. If present, also turn on *Disable permission monitoring* / *Install via USB*.
3. Settings > Security and privacy > **Auto Blocker**: turn it **off**, otherwise it can block USB commands.
4. Connect the phone by USB, accept the *Allow USB debugging* prompt, then run `adb devices` and copy the serial.
5. Run any test with that serial, for example:
   ```
   gradle test --tests "tests.ApiDemosApkTest" -DdeviceUdid=R58XXXXXXXX
   ```
   Close the emulator first, or keep it running and rely on `-DdeviceUdid` to choose the target.

> The built-in Settings test uses English menu text from stock Android. Samsung's One UI names some entries
> differently, so the APK and gesture tests are the ones to run on the real phone.

## 4. Screenshots

| Deliverable | File |
|---|---|
| appium doctor output | `screenshots/01-appium-doctor.png` |
| Built-in app on emulator | `screenshots/02-builtin-emulator.png` |
| APK on emulator | `screenshots/03-apk-emulator.png` |
| Real device run | `screenshots/04-real-device.png` |
| Tap / Scroll / Slide / Drag & Drop | `screenshots/05-*.png` |
| Chrome web test | `screenshots/06-chrome.png` |

## 5. Cheat sheet

See [docs/Appium-Java-Cheat-Sheet.md](docs/Appium-Java-Cheat-Sheet.md).
