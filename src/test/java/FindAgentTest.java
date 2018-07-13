import AppiumUtil.ContextHandler;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class FindAgentTest extends SauceTestBase
{
	@Before
	public void setup() throws MalformedURLException
	{
		URL url = getSauceURL();

		DesiredCapabilities capabilities = SauceTestBase.getIPadProSimulatorCapabilities();
		capabilities.setCapability("name", "Allstate Find Agent switch tabs by switching context");

		driver = new IOSDriver<WebElement>(url, capabilities);
		wait = new WebDriverWait(driver, 60);
	}

	@Test
	public void switchTabsBySwitchingContext() throws MalformedURLException
	{
		// OPEN HOME PAGE
		System.out.println("Open " + Allstate.HomePage.url);
		driver.get(Allstate.HomePage.url);

		// INITIAL CONTEXT
		ContextHandler.printContextInfo(driver);

		// CLICK LINK THAT OPENS IN A NEW TAB
		System.out.println("Click " + Allstate.HomePage.findAgentLink);
		wait.until(ExpectedConditions.elementToBeClickable(Allstate.HomePage.findAgentLink)).click();

		// CONTEXT AFTER OPENING NEW TAB
		ContextHandler.printContextInfo(driver);
		assertThat(driver.getTitle(), is(equalTo(Allstate.FindAgentPage.title)));

		// SWITCH CONTEXT
		System.out.println("Switch context"); // note switching app context / not window handle
		ContextHandler.switchWebContext(driver);

		// CONTEXT AFTER SWITCHING BACK
		ContextHandler.printContextInfo(driver);
		assertThat(driver.getTitle(), is(equalTo(Allstate.HomePage.title)));
	}

	@After
	public void teardown()
	{
		driver.quit();
	}
}