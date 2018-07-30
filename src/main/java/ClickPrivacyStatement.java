import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;
import java.util.Set;

import java.util.concurrent.TimeUnit;


public class ClickPrivacyStatement
{
	public static void main(String[] args) throws MalformedURLException
	{
		boolean localTests = (System.getenv("LOCAL_APPIUM") != null);
		System.out.println("Running against " + (localTests ? "local Appium" : "Sauce") + " server");
		URL url = localTests ? getLocalURL() : getSauceURL();
		DesiredCapabilities capabilities = useIPadPro12Inch();
		AppiumDriver<WebElement> driver = new IOSDriver<>(url, capabilities);
		System.out.println("Session started");
		WebDriverWait wait = new WebDriverWait(driver, 60);

		driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);

		try {
			// driver.get("https://www-stest.allstate.com/resources/Images/Bumper2Bumper/en/badge_b2b.png?v=dd7248d5-4330-fe82-09d9-712b3a90b129");
			// sleep(10);
			//
			// driver.get("https://www-stest.allstate.com/resources/Images/Bumper2Bumper/en/content-home/bubble_ohno.png?v=dd7248d5-4330-fe82-09d9-712b3a90b129");
			// sleep(10);
			//
			// driver.get("https://www-stest.allstate.com/resources/Images/Bumper2Bumper/en/content-home/bubble_mycar.png?v=dd7248d5-4330-fe82-09d9-712b3a90b129");
			// sleep(10);
			//
			// driver.get("https://www-stest.allstate.com/resources/Images/Bumper2Bumper/en/content-home/bubble_yikes.png?v=dd7248d5-4330-fe82-09d9-712b3a90b129");
			// sleep(10);
			//
			// driver.get("https://www-stest.allstate.com/resources/Images/Bumper2Bumper/en/content-home/sound.png?v=885ec7ca-3000-576d-ab96-a1cd84cdf59f");
			// sleep(10);
			//
			// driver.get("https://www-stest.allstate.com/resources/Images/Bumper2Bumper/en/content-home/trunk_static.png?v=885ec7ca-3000-576d-ab96-a1cd84cdf59f");
			// sleep(10);
			//
			// driver.get("https://www-stest.allstate.com/resources/Images/Bumper2Bumper/en/content-home/car_left.png?v=dd7248d5-4330-fe82-09d9-712b3a90b129");
			// sleep(10);
			//
			// driver.get("https://www-stest.allstate.com/resources/Images/Bumper2Bumper/en/content-home/car_right.png?v=dd7248d5-4330-fe82-09d9-712b3a90b129");
			// sleep(10);
			//
			// driver.get("https://www-stest.allstate.com/resources/Images/Bumper2Bumper/en/content-home/wheel2_anim.gif");
			// sleep(10);
			//
			// driver.get("https://www-stest.allstate.com/resources/Images/Bumper2Bumper/en/content-home/wheel2_static.gif");
			// sleep(10);
			//
			// driver.get("https://www-stest.allstate.com/resources/Images/Bumper2Bumper/en/content-home/wheel_anim.gif");
			// sleep(10);
			//
			// driver.get("https://www-stest.allstate.com/resources/Images/Bumper2Bumper/en/content-home/wheel_static.gif");
			// sleep(10);

			// String currentContext = driver.getContext();
			// driver.context("NATIVE_APP");
			//
			// try {
			// 	WebElement urlBar = driver.findElementByAccessibilityId("URL");
			//
			// 	urlBar.sendKeys("https://www-stest.allstate.com/anon/bumpertobumper/default.aspx\n");
			// 	System.out.println("Sent address manually");
			// } catch (Exception e) {
			// 	System.out.println("Manual URL manipulation failed. Trying normal way");
			// 	driver.context(currentContext);
			//
			// 	driver.get("https://www-stest.allstate.com/anon/bumpertobumper/default.aspx");
			// } finally {
			// 	if (driver.getContext() != currentContext) {
			// 		driver.context(currentContext);
			// 	}
			// }

			driver.get("https://www-stest.allstate.com/anon/bumpertobumper/default.aspx");
			System.out.println("Sent address");

			sleep(10);
			System.out.println("Waited 10 seconds...");

			System.out.println(driver.executeScript("document.getElementById('car_right').style"));

			// driver.get("https://www-stest.allstate.com/anon/bumpertobumper/default.aspx");
			// System.out.println("Sent address again");

			// LogEntries logEntries = driver.manage().logs().get("syslog");
			// for (LogEntry entry : logEntries) {
      //     System.out.println(new Date(entry.getTimestamp()) + ": " + entry.getLevel() + ": " + entry.getMessage());
      //     //do something useful with the data
      // }

			// System.out.println(driver.executeScript("return stageTwo;"));
			// driver.executeScript("stageTwo = function () {}; stageThree = function () {};");
			// System.out.println("Did some JS");

			try {
				// wait.until(ExpectedConditions.titleIs("Allstate Bumper-to-Bumper Basics | Auto Insurance Guide"));
				String title = "";
				for (int i = 0; i < 10; i++) {
					title = driver.getTitle();
					System.out.println("Title: '" + title + "'");
					System.out.println(title == "Allstate Bumper-to-Bumper Basics | Auto Insurance Guide");
					if (title == "Allstate Bumper-to-Bumper Basics | Auto Insurance Guide") {
						break;
					}
				}
				if (title != "Allstate Bumper-to-Bumper Basics | Auto Insurance Guide") {
					throw new Exception("No title right");
				}
				System.out.println("Title is right");
			} catch (Exception e) {
				System.out.println("Title never appeared. Continuing...");
				driver.executeScript("return window.stop()");
			}

			waitForPageToLoad(driver);
			printInfo(driver);

			// // Giant iPads don't work for clicking with nativeWebTap :(
			// // so try with native tap
			// // this is fixed in 1.8.2
			// String currentContext = driver.getContext();
			// driver.context("NATIVE_APP");
			//
			// try {
			// 	WebElement link = driver.findElementByAccessibilityId("Privacy Statement");
			//
			// 	// do the native action
			// 	link.click();
			// 	System.out.println("found and clicked (native): " + link);
			// } catch (Exception e) {
			// 	// print out the rectangle so we can see how nativeWebTap is mis-calculating
			// 	// the coordinates :( :(
			// 	WebElement link = driver.findElementByAccessibilityId("Privacy Statement");
			// 	Rectangle rect = link.getRect();
			// 	System.out.println("x: " + rect.getX() + ", y: " + rect.getY() + ", width: " + rect.getWidth() + ", height: " + rect.getHeight());
			//
			// 	// failed! go back into the web context and pray harder
			// 	driver.context(currentContext);
			//
			// 	link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Privacy Statement")));
			// 	link.click();
			// 	System.out.println("found and clicked (web): " + link);
			// } finally {
			// 	if (driver.getContext() != currentContext) {
			// 		driver.context(currentContext);
			// 	}
			// }
			//
			// // this will not be necessary in 1.8.2, when that is released
			// try {
			// 	wait.until(ExpectedConditions.titleIs("Allstate.com - Learn More About Allstate Insurance Company"));
			// } catch (Exception e) {
			// 	switchWebContext(driver);
			// 	wait.until(ExpectedConditions.titleIs("Allstate.com - Learn More About Allstate Insurance Company"));
			// }
			//
			// waitForPageToLoad(driver);
			// printInfo(driver);
		} finally {
			driver.quit();
		}
	}

	public static URL getLocalURL() throws MalformedURLException
	{
		return new URL("http://0.0.0.0:4723/wd/hub");
	}

	public static URL getSauceURL() throws MalformedURLException
	{
		String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
		String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
		String SAUCE_URL = "https://SAUCE_USERNAME:SAUCE_ACCESS_KEY@ondemand.saucelabs.com/wd/hub"
				.replace("SAUCE_USERNAME", SAUCE_USERNAME)
				.replace("SAUCE_ACCESS_KEY", SAUCE_ACCESS_KEY);

		return new URL(SAUCE_URL);
	}

	public static DesiredCapabilities useIPadPro12Inch()
	{
		DesiredCapabilities capabilities = getIOSSimulator();
		capabilities.setCapability("deviceName", "iPad Pro (12.9-inch) (2nd generation)");
		return capabilities;
	}

	public static DesiredCapabilities useIPadPro9Inch()
	{
		DesiredCapabilities capabilities = getIOSSimulator();
		capabilities.setCapability("deviceName", "iPad Pro (9.7 inch) Simulator");
		return capabilities;
	}

	public static DesiredCapabilities useIphone7()
	{
		DesiredCapabilities capabilities = getIOSSimulator();
		capabilities.setCapability("deviceName", "iPhone 7 Simulator");
		return capabilities;
	}

	public static DesiredCapabilities getIOSSimulator()
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		// capabilities.setCapability("appiumVersion", "{'appium-url': 'sauce-storage:appium.zip'}");
		// capabilities.setCapability("appiumVersion", "1.8.1");
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "11.3");
		capabilities.setCapability("deviceName", "iPhone Simulator");
		capabilities.setCapability("browserName", "Safari");
		capabilities.setCapability("nativeWebTap", true);
		capabilities.setCapability("autoAcceptAlerts", true);
		// capabilities.setCapability("locationServicesAuthorized", true);
		// capabilities.setCapability("showIOSLog", false);
		capabilities.setCapability("showSafariConsoleLog", true);
		capabilities.setCapability("name", "Allstate - Click Privacy Statement Link");
		return capabilities;
	}

	public static void printInfo(AppiumDriver<WebElement> driver)
	{
		System.out.println("------");
		System.out.println("CONTEXT HANDLES: " + driver.getContextHandles());
		System.out.println("CURRENT CONTEXT: " + driver.getContext());
		System.out.println("WINDOW HANDLES: " + driver.getWindowHandle());
		System.out.println("CURRENT WINDOW: " + driver.getWindowHandle());
		System.out.println("TITLE: " + driver.getTitle());
		System.out.println("URL: " + driver.getCurrentUrl());
		System.out.println("------");
	}

	public static void waitForPageToLoad(AppiumDriver<WebElement> driver)
	{
		for (int i = 0; i < 3; i++) {
			System.out.print("Checking readyState... ");
			String readyState = driver.executeScript("return (document.readyState == 'complete' && jQuery.active == 0);").toString();
			System.out.println(readyState);

			if (readyState.equalsIgnoreCase("true")) {
				driver.executeScript("return window.stop()");
				return;
			}
		}

		driver.executeScript("return window.stop()");
	}

	public static void sleep(int ms)
	{
		try
		{
			Thread.sleep(1000 * ms);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void switchWebContext(AppiumDriver driver)
	{
		String newContext = getNewWebContext(driver);

		if (newContext != null)
		{
			System.out.println("switching to new context: " + newContext);
			driver.context(newContext);
		}
	}

	// This assumes there are only two web contexts
	public static String getNewWebContext(AppiumDriver driver)
	{
		String currentContext = driver.getContext();
		Set<String> contextHandles = driver.getContextHandles();

		for (String contextHandle : contextHandles)
		{
			if (contextHandle.startsWith("WEBVIEW") && ! contextHandle.equals(currentContext))
			{
				return contextHandle;
			}
		}

		return null;
	}
}
