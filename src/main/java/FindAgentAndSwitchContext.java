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

public class FindAgentAndSwitchContext
{
	private static final String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	private static final String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	private static final String SAUCE_URL = "https://" + SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";

	public static void main(String[] args) throws MalformedURLException
	{
		// SETUP
		URL url = new URL(SAUCE_URL);
		DesiredCapabilities capabilities = getIPadProSimulatorCapabilities();
		AppiumDriver<WebElement> driver = new IOSDriver<WebElement>(url, capabilities);
		WebDriverWait wait = new WebDriverWait(driver, 60);

		// OPEN HOME PAGE
		System.out.println("Open " + Allstate.HomePage.url);
		driver.get(Allstate.HomePage.url);

		// INITIAL CONTEXT
		printContextWindowAndTitle(driver);

		// CLICK LINK THAT OPENS IN A NEW TAB
		System.out.println("Click " + Allstate.HomePage.findAgentLink);
		wait.until(ExpectedConditions.elementToBeClickable(Allstate.HomePage.findAgentLink)).click();

		// WAIT A FEW SECONDS
		try { Thread.sleep(5000); } catch (InterruptedException e) {}

		// CONTEXT AFTER OPENING NEW TAB
		printContextWindowAndTitle(driver);
		assert(driver.getTitle().contains("Agent"));

		// SWITCH CONTEXT
		System.out.println("Switch context");
		switchWebContext(driver);

		// CONTEXT AFTER SWITCHING BACK
		printContextWindowAndTitle(driver);
		assert(driver.getTitle().equals(Allstate.HomePage.title));

		driver.quit();
	}

	public static DesiredCapabilities getIPadProSimulatorCapabilities()
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("appiumVersion", "1.8.1");
		capabilities.setCapability("platformVersion","11.3");
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("deviceName","iPad Pro (12.9 inch) (2nd generation) Simulator");
		capabilities.setCapability("deviceOrientation", "portrait");
		capabilities.setCapability("browserName", "Safari");
		capabilities.setCapability("locationServicesAuthorized", true);
		capabilities.setCapability("nativeWebTap", true);
		capabilities.setCapability("name", "Allstate Find Agent and switch context");

		return capabilities;
	}

	public static void switchWebContext(AppiumDriver driver)
	{
		String newContext = getNewContext(driver);
		if (newContext != null)
		{
			System.out.println("switching to new context: " + newContext);
			driver.context(newContext);
		}
	}

	// This assumes there are only two contexts
	public static String getNewContext(AppiumDriver driver)
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

	public static void printContextWindowAndTitle(AppiumDriver driver)
	{
		System.out.println("------");
		System.out.println("CONTEXT HANDLES: " + driver.getContextHandles());
		System.out.println("CURRENT CONTEXT: " + driver.getContext());
		System.out.println("WINDOW HANDLES: " + driver.getWindowHandle());
		System.out.println("CURRENT WINDOW: " + driver.getWindowHandle());
		System.out.println("TITLE: " + driver.getTitle());
		System.out.println("------");
	}

	public static class Allstate
	{
		public static class HomePage
		{
			public static String url =  "https://www-stest.allstate.com/";
			public static String title = "Auto Insurance Quotes - Car Insurance | Allstate Online Quote";
			public static By findAgentLink = By.linkText("Find An Agent");
		}

		public static class FindAgentPage
		{
			public static String url = "https://agents.allstate.com/locator.html?zip=&intcid=%2Fhome%2Fhome.aspx%7CMainNav%7Cfindagent";
//			public static String title = "Find Insurance Agents Near You | Allstate";
			public static String title = "Insurance Agent Locator | Allstate";
		}
	}
}