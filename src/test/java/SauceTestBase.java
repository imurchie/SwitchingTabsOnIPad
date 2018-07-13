import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.SauceREST;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;

@RunWith(ConcurrentParameterized.class)
public class SauceTestBase implements SauceOnDemandSessionIdProvider
{
	public String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	public String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");

	protected SauceREST api = new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY);
	protected SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(SAUCE_USERNAME, SAUCE_ACCESS_KEY);

	protected String platformName;
	protected String platformVersion;
	protected String deviceName;
	protected String browserName;

	protected URL url;
	protected DesiredCapabilities capabilities;
	protected AppiumDriver<WebElement> driver;
	protected String sessionId;
	protected WebDriverWait wait;

	@Rule
	public TestName testName = new TestName();

	@Rule
	public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

	public SauceTestBase(String platformName, String platformVersion, String deviceName, String browserName)
	{
		this.platformName = platformName;
		this.platformVersion = platformVersion;
		this.deviceName = deviceName;
		this.browserName = browserName;
	}

	public  URL getSauceURL(String SAUCE_USERNAME, String SAUCE_ACCESS_KEY) throws MalformedURLException
	{
		String SAUCE_URL = "https://" + SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
		return new URL(SAUCE_URL);
	}

	public DesiredCapabilities getAppiumCapabilities(String platformName, String platformVersion, String deviceName, String browserName)
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("appiumVersion", "1.8.1");
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("deviceOrientation", "portrait");
		capabilities.setCapability("browserName", browserName);
		capabilities.setCapability("locationServicesAuthorized", true);
		capabilities.setCapability("nativeWebTap", true);
		capabilities.setCapability("name", this.getClass().getName() + " " + testName.getMethodName());

		return capabilities;
	}

	@Before
	public void setup() throws MalformedURLException
	{
		url = getSauceURL(SAUCE_USERNAME, SAUCE_ACCESS_KEY);
		capabilities = getAppiumCapabilities(platformName, platformVersion, deviceName, browserName);
		capabilities.setCapability("name", this.getClass().getName() + testName.getMethodName());

		driver = new IOSDriver<WebElement>(url, capabilities);
		sessionId = driver.getSessionId().toString();
		wait = new WebDriverWait(driver, 60);
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
