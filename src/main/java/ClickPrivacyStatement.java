import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Alert;
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
		// URL url = getSauceURL();
		URL url = getLocalURL();
		DesiredCapabilities capabilities = useIPadPro12Inch();
		AppiumDriver<WebElement> driver = new IOSDriver<>(url, capabilities);
		WebDriverWait wait = new WebDriverWait(driver, 60);

		try {
			driver.get("https://www-stest.allstate.com/anon/bumpertobumper/default.aspx");

			try {
				wait.until(ExpectedConditions.titleIs("Allstate Bumper-to-Bumper Basics | Auto Insurance Guide"));
				System.out.println("Title is right");
			} catch (Exception e) {
				System.out.println("Title never appeared. Continuing...");
			}

			waitForPageToLoad(driver);
			printInfo(driver);

			// Giant iPads don't work for clicking with nativeWebTap :(
			// so try with native tap
			String currentContext = driver.getContext();
			driver.context("NATIVE_APP");

			try {
				WebElement link = driver.findElementByAccessibilityId("Privacy Statement");

				// print out the rectangle so we can see how nativeWebTap is mis-calculating
				// the coordinates
				Rectangle rect = link.getRect();
				System.out.println("x: " + rect.getX() + ", y: " + rect.getY() + ", width: " + rect.getWidth() + ", height: " + rect.getHeight());

				// do the native action
				link.click();
				System.out.println("found and clicked (native): " + link);
			} catch (Exception e) {
				// failed! go back into the web context and pray harder
				driver.context(currentContext);

				WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Privacy Statement")));
				link.click();
				System.out.println("found and clicked (web): " + link);
			} finally {
				if (driver.getContext() != currentContext) {
					driver.context(currentContext);
				}
			}

			// this is not necessary in 1.8.2, when that is released
			sleep(10);
			switchWebContext(driver);
			sleep(10);

			wait.until(ExpectedConditions.titleIs("Allstate.com - Learn More About Allstate Insurance Company"));

			waitForPageToLoad(driver);
			printInfo(driver);
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
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "11.3");
		capabilities.setCapability("deviceName", "iPhone Simulator");
		capabilities.setCapability("browserName", "Safari");
		capabilities.setCapability("nativeWebTap", true);
		capabilities.setCapability("autoAcceptAlerts", true);
		capabilities.setCapability("locationServicesAuthorized", true);
		capabilities.setCapability("showIOSLog", false);
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
		for(int i = 0; i < 3; i++) {
			System.out.print("Checking readyState... ");
			String readyState = driver.executeScript("return document.readyState;").toString();
			System.out.println(readyState);

			if (readyState.equalsIgnoreCase("complete")) {
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
