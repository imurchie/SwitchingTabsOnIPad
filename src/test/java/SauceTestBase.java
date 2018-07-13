import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceTestBase
{
	public String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	public String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");

	protected AppiumDriver<WebElement> driver;
	protected WebDriverWait wait;

	public  URL getSauceURL() throws MalformedURLException
	{
		String SAUCE_URL = "https://" + SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
		return new URL(SAUCE_URL);
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

		return capabilities;
	}
}
