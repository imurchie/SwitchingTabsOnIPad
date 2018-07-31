import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Set;


public class ClickPrivacyStatement
{
	public static void main(String[] args) throws MalformedURLException
	{
		// switch between Sauce and local server
		boolean localTests = (System.getenv("LOCAL_APPIUM") != null);
		System.out.println("Running against " + (localTests ? "local Appium" : "Sauce") + " server");

		// switch between pages to load
		boolean animations = (System.getenv("ANIMATIONS") != null);
		String page = "https://imurchie.github.io/SwitchingTabsOnIPad/test-" + (animations ? "on" : "off") + ".html";
		System.out.println("Using page " + (animations ? "with" : "without") + " animations: '" + page + "'\n");

		// start the session
		URL url = localTests ? getLocalURL() : getSauceURL();
		DesiredCapabilities capabilities = useIPadPro12Inch(animations);
		AppiumDriver<WebElement> driver = new IOSDriver<>(url, capabilities);
		System.out.println("Session started");
		WebDriverWait wait = new WebDriverWait(driver, 60);

		try {
			driver.get(page);
			System.out.println("Requested page: '" + page + "'");

			waitForTitle(driver, "Allstate Bumper-to-Bumper Basics | Auto Insurance Guide");

			waitForPageToLoad(driver);
			printInfo(driver);

			// Giant iPads don't work for clicking with nativeWebTap :(
			// so try with a real native tap. this is fixed in 1.8.2
			String currentContext = driver.getContext();
			driver.context("NATIVE_APP");

			try {
				WebElement link = driver.findElementByAccessibilityId("Privacy Statement");

				// do the native action
				link.click();
				System.out.println("found and clicked (native): " + link);
			} catch (Exception e) {
				// print out the rectangle so we can see how nativeWebTap is mis-calculating
				// the coordinates :( :(
				WebElement link = driver.findElementByAccessibilityId("Privacy Statement");
				Rectangle rect = link.getRect();
				System.out.println("x: " + rect.getX() + ", y: " + rect.getY() + ", width: " + rect.getWidth() + ", height: " + rect.getHeight());

				// failed! go back into the web context and pray harder
				driver.context(currentContext);

				// find the web link this time
				link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Privacy Statement")));
				link.click();
				System.out.println("found and clicked (web): " + link);
			} finally {
				// get us back into the web context, if necessary
				if (driver.getContext() != currentContext) {
					driver.context(currentContext);
				}
			}

			// this will not be necessary in 1.8.2, when that is released
			try {
				wait.until(ExpectedConditions.titleIs("Allstate.com - Learn More About Allstate Insurance Company"));
			} catch (Exception e) {
				switchWebContext(driver);
				wait.until(ExpectedConditions.titleIs("Allstate.com - Learn More About Allstate Insurance Company"));
			}

			waitForPageToLoad(driver);
			printInfo(driver);
		} finally {
			// no matter what, end the session
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

	public static DesiredCapabilities useIPadPro12Inch(boolean animations)
	{
		DesiredCapabilities capabilities = getIOSSimulator(animations);
		capabilities.setCapability("deviceName", "iPad Pro (12.9-inch) (2nd generation)");
		return capabilities;
	}

	public static DesiredCapabilities useIPadPro9Inch(boolean animations)
	{
		DesiredCapabilities capabilities = getIOSSimulator(animations);
		capabilities.setCapability("deviceName", "iPad Pro (9.7 inch) Simulator");
		return capabilities;
	}

	public static DesiredCapabilities useIphone7(boolean animations)
	{
		DesiredCapabilities capabilities = getIOSSimulator(animations);
		capabilities.setCapability("deviceName", "iPhone 7 Simulator");
		return capabilities;
	}

	public static DesiredCapabilities getIOSSimulator(boolean animations)
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "11.3");
		capabilities.setCapability("deviceName", "iPhone Simulator");
		capabilities.setCapability("browserName", "Safari");
		capabilities.setCapability("nativeWebTap", true);
		capabilities.setCapability("autoAcceptAlerts", true);
		capabilities.setCapability("showIOSLog", false);
		capabilities.setCapability("showSafariConsoleLog", true);
		capabilities.setCapability("name", "Allstate - Click Privacy Statement Link - animations " + (animations ? "on" : "off"));
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

	public static void waitForPageToLoad(AppiumDriver<WebElement> driver) {
		for (int i = 0; i < 10; i++) {
			System.out.print("Checking readyState... ");
			String readyState = driver.executeScript("return document.readyState;").toString();
			System.out.println(readyState);

			if (readyState.equalsIgnoreCase("complete")) {
				return;
			}
		}

		System.out.println("Ready state never completed. Killing load...");
		driver.executeScript("return window.stop()");
	}

	public static void waitForTitle(AppiumDriver<WebElement> driver, String expectedTitle) {
		// just to give visibility into the title as it is retrieved
		try {
			// wait.until(ExpectedConditions.titleIs("Allstate Bumper-to-Bumper Basics | Auto Insurance Guide"));
			String title = "";
			for (int i = 0; i < 30; i++) {
				title = driver.getTitle();
				System.out.println("Title: " + title);
				if (title.equals(expectedTitle)) {
					break;
				}
				sleep(1);
			}
			if (!title.equals(expectedTitle)) {
				throw new Exception("Title not found. Found: " + title);
			}
			System.out.println("Title found");
		} catch (Exception e) {
			System.out.println("Error polling title: " + e.getMessage());
			System.out.println("Title never appeared. Continuing...");
		}
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
