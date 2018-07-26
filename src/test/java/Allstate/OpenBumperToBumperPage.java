package Allstate;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.SauceREST;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

@RunWith(ConcurrentParameterized.class)
public class OpenBumperToBumperPage implements SauceOnDemandSessionIdProvider
{
	public String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	public String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	public String SAUCE_URL = "https://" + SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY + "@ondemand.saucelabs.com/wd/hub";

	protected SauceREST api = new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY);
	protected SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(SAUCE_USERNAME, SAUCE_ACCESS_KEY);

	protected URL url;
	protected DesiredCapabilities capabilities;
	protected String sessionId;
	protected IOSDriver driver;
	protected WebDriverWait wait;

	protected String platformName;
	protected String platformVersion;
	protected String deviceName;
	protected String browserName;

	@ConcurrentParameterized.Parameters
	public static LinkedList getDevices()
	{
		return new LinkedList() {{
			add(new String[] {"iOS", "11.3", "iPad Pro (12.9 inch) (2nd generation) Simulator", "Safari"});
			add(new String[] {"iOS", "11.3", "iPad Pro (12.9 inch) (2nd generation) Simulator", "Safari"});
			add(new String[] {"iOS", "11.3", "iPad Pro (12.9 inch) (2nd generation) Simulator", "Safari"});
			add(new String[] {"iOS", "11.3", "iPad Pro (9.7 inch) Simulator", "Safari"});
			add(new String[] {"iOS", "11.3", "iPhone 7 Simulator", "Safari"});
		}};
	}

	public OpenBumperToBumperPage(String platformName, String platformVersion, String deviceName, String browserName)
	{
		this.platformName = platformName;
		this.platformVersion = platformVersion;
		this.deviceName = deviceName;
		this.browserName = browserName;
	}

	@Rule
	public TestName testName = new TestName();

	@Rule
	public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

	@Test
	public void onIpadPro2() throws MalformedURLException
	{
		url = new URL(SAUCE_URL);

		capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("browserName", browserName);
		capabilities.setCapability("nativeWebTap", true);
		capabilities.setCapability("autoAcceptAlerts", true);
		capabilities.setCapability("locationServicesAuthorized", true);
		capabilities.setCapability("safariAllowPopups", true);
		capabilities.setCapability("name", this.getClass().getName() + testName.getMethodName() );

		driver = new IOSDriver(url, capabilities);
		System.out.println("DRIVER: " + driver);

		sessionId = driver.getSessionId().toString();
		System.out.println("SESSION ID: " + sessionId);

		driver.get("https://www-stest.allstate.com/anon/bumpertobumper/default.aspx");
		System.out.println("opened URL: " + "https://www-stest.allstate.com/anon/bumpertobumper/default.aspx");

		System.out.println("TITLE: " + driver.getTitle());

		driver.quit();
	}

	@Override
	public String getSessionId()
	{
		return sessionId;
	}
}
