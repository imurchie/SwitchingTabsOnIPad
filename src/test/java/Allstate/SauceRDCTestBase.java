package Allstate;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.SauceREST;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceRDCTestBase implements SauceOnDemandSessionIdProvider
{
	public static String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	public static String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");

	public static String LOCAL_APPIUM_URL = "http://localhost:4723/wd/hub";
	public static String SAUCE_URL = "https://" + SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY + "@ondemand.saucelabs.com/wd/hub";
	public static String TESTOBJECT_URL = "https://us1.appium.testobject.com/wd/hub";

	protected SauceREST api = new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY);
	protected SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(SAUCE_USERNAME, SAUCE_ACCESS_KEY);

	protected String platformName;
	protected String platformVersion;
	protected String deviceName;
	protected String browserName;
	protected String appiumUrl = TESTOBJECT_URL;

	protected URL url;
	protected DesiredCapabilities capabilities;
	protected AppiumDriver<WebElement> driver;
	protected String sessionId;
	protected WebDriverWait wait;

	@Rule
	public TestName testName = new TestName();

//	@Rule
//	public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

	public SauceRDCTestBase(String platformName, String platformVersion, String deviceName, String browserName)
	{
		this.platformName = platformName;
		this.platformVersion = platformVersion;
		this.deviceName = deviceName;
		this.browserName = browserName;
	}

	public DesiredCapabilities getAppiumCapabilities(String platformName, String platformVersion, String deviceName, String browserName)
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("browserName", browserName);
//		capabilities.setCapability("nativeWebTap", true);
		capabilities.setCapability("safariAllowPopups", true);

		capabilities.setCapability("name", this.getClass().getName() + " " + testName.getMethodName() + " on " + deviceName);

		capabilities.setCapability("testobject_api_key", System.getenv("TESTOBJECT_API_KEY"));

		return capabilities;
	}

	@Before
	public void setup() throws MalformedURLException
	{
		url = new URL(appiumUrl);

		capabilities = getAppiumCapabilities(platformName, platformVersion, deviceName, browserName);
		System.out.println("DESIRED CAPABILITIES: " + capabilities);

		driver = new IOSDriver<WebElement>(url, capabilities);
		sessionId = driver.getSessionId().toString();
		wait = new WebDriverWait(driver, 60);

		System.out.println("DRIVER: " + driver + driver.getCapabilities());
	}

	@After
	public void teardown()
	{
		driver.quit();
	}


	@Override
	public String getSessionId()
	{
		return sessionId;
	}
}
