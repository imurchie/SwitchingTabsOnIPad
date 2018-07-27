package Allstate.Scenarios;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class StopPageLoad
{
	public static void main(String[] args) throws MalformedURLException
	{
		URL url = getSauceURL();
		DesiredCapabilities capabilities = useIPadPro12Inch();
		AppiumDriver<WebElement> driver = new IOSDriver<>(url, capabilities);
		WebDriverWait wait = new WebDriverWait(driver, 60);

		driver.get("https://www-stest.allstate.com/anon/bumpertobumper/default.aspx");

		waitForPageToLoad(driver);
		printInfo(driver);

		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Privacy Statement"))).click();

		sleep(10);
		switchWebContext(driver);
		sleep(10);

		waitForPageToLoad(driver);
		printInfo(driver);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/about/NY-domestic-violence-notice.aspx']")));

		waitForPageToLoad(driver);
		printInfo(driver);
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
		capabilities.setCapability("deviceName", "iPad Pro (12.9 inch) (2nd generation) Simulator");
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
		capabilities.setCapability("safariAllowPopups", true);
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
		for(int i=0; i<10; i++)
		{
			String readyState = driver.executeScript("return document.readyState;").toString();
			System.out.println("readyState: " + readyState);

			if (readyState.equalsIgnoreCase("complete")) { return; }

			sleep(5);
		}

		driver.executeScript("return window.stop();");
	}

	public static void sleep(int seconds)
	{
		try
		{
			Thread.sleep(1000 * seconds);
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