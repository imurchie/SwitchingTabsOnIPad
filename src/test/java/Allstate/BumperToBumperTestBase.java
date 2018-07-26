package Allstate;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class BumperToBumperTestBase
{
	public static String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	public static String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	public static String SAUCE_URL = "https://" + SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY + "@ondemand.saucelabs.com/wd/hub";

	static String TESTOBJECT_URL = "https://us1.appium.testobject.com/wd/hub";
	static String TESTOBJECT_API_KEY = System.getenv("TESTOBJECT_API_KEY");

	public static String LOCAL_APPIUM_URL = "http://localhost:4723/wd/hub";

	@Rule
	public TestName testName = new TestName();

//	@Rule
//	public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();

	URL url;
	DesiredCapabilities capabilities;
	IOSDriver<WebElement> driver;
	WebDriverWait wait;
	String sessionId;

	@After
	public void teardown()
	{
		if (driver != null)
		{
			System.out.print("Test Result URL: " + driver.getCapabilities().getCapability("testobject_test_report_url"));
			driver.quit();
		}
	}

	protected void executeSteps()
	{
		System.out.println("opening " + BumperToBumperPage.url);
		driver.get(BumperToBumperPage.url);

		checkReadyState();
//		printInfo();

		WebElement privacyStatementLink = wait.until(
				ExpectedConditions.elementToBeClickable(
						BumperToBumperPage.PrivacyStatementLink));

		System.out.println("found element: " + privacyStatementLink);

		privacyStatementLink.click();
		System.out.println("clicked on element: " + privacyStatementLink);

		String context = driver.getContext();
		driver.context("NATIVE_APP");
		System.out.println("switched to native context");
		System.out.println(driver.getContextHandles());
		System.out.println(driver.getContext());

		driver.findElement(MobileBy.id("Allow")).click();
		System.out.println("clicked Allow");

		System.out.println("switch back to web context");
		driver.context(context);

//		printInfo();

		AppiumUtil.ContextHandler.switchWebContext(driver);

//		printInfo();
		checkReadyState();

		wait.until(ExpectedConditions.titleIs(PrivacyStatementPage.title));

		assertThat(driver.getTitle()).isEqualTo(PrivacyStatementPage.title);
		assertThat(driver.getCurrentUrl()).isEqualTo(PrivacyStatementPage.url);
	}

	protected void useLocalIpadSimulator() throws MalformedURLException
	{
		url = new URL(LOCAL_APPIUM_URL);

		capabilities = new DesiredCapabilities() {{
			setCapability("platformName", "iOS");
			setCapability("platformVersion", "11.4");
			setCapability("deviceName", "iPad Pro (12.9-inch) (2nd generation)");
			setCapability("browserName", "Safari");
//			setCapability("nativeWebTap", true);
			setCapability("safariAllowPopups", true);
			setCapability("autoAcceptAlerts", true);
			setCapability("name", this.getClass().getName() + " " + testName.getMethodName());
		}};

		createDriver();
	}

	protected void useSauceIpadSimulator() throws MalformedURLException
	{
		url = new URL(SAUCE_URL);

		capabilities = new DesiredCapabilities() {{
			setCapability("platformName", "iOS");
			setCapability("platformVersion", "11.3");
			setCapability("deviceName", "iPad Pro (12.9 inch) (2nd generation) Simulator");
			setCapability("browserName", "Safari");
//			setCapability("nativeWebTap", true);
			setCapability("safariAllowPopups", true);
			setCapability("autoAcceptAlerts", true);
			setCapability("name", this.getClass().getName() + " " + testName.getMethodName());
		}};

		createDriver();
		wait = new WebDriverWait(driver, 60);
	}

	protected void useSauceIpadRDC() throws MalformedURLException
	{
		url = new URL(TESTOBJECT_URL);

		capabilities = new DesiredCapabilities() {{
			setCapability("platformName", "iOS");
			setCapability("deviceName", "iPad Pro 12.9");
			setCapability("browserName", "Safari");
			setCapability("nativeWebTap", true);
			setCapability("safariAllowPopups", true);
			setCapability("autoAcceptAlerts", true);
			setCapability("name", getTestName());
			setCapability("testobject_api_key", TESTOBJECT_API_KEY);
		}};

		createDriver();
	}


	protected void createDriver()
	{
		System.out.println("DESIRED CAPABILITIES: " + capabilities);

		driver = new IOSDriver<>(url, capabilities);
		System.out.println("DRIVER: " + driver);

		sessionId = driver.getSessionId().toString();
		System.out.println("SESSION ID: " + sessionId);

		wait = new WebDriverWait(driver, 20);
//		resultWatcher.setRemoteWebDriver(driver);
	}

	protected void printInfo()
	{
		System.out.println("----------");
		System.out.println("TITLE: " + driver.getTitle());
		System.out.println("URL: " + driver.getCurrentUrl());
		System.out.println("CONTEXTS: " + driver.getContextHandles());
		System.out.println("CURRENT CONTEXT: " + driver.getContext());
		System.out.println("WINDOWS: " + driver.getWindowHandles());
		System.out.println("CURRENT WINDOW: " + driver.getWindowHandle());
		System.out.println("----------");
	}

	protected void checkReadyState()
	{
		for (int i=0; i<10; i++)
		{
			String readyState = driver.executeScript("return document.readyState;").toString();
			System.out.println("READYSTATE: " + readyState);

			if (readyState.equals("complete")) { return; }

			sleep(5);
		}
	}

	protected void sleep(int seconds)
	{
		try
		{
			Thread.sleep(seconds * 1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	protected String getTestName()
	{
		return this.getClass().getName() + " " + testName.getMethodName();
	}
}
