package Allstate;

import AppiumUtil.ContextHandler;
import com.saucelabs.junit.ConcurrentParameterized;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.MalformedURLException;
import java.util.LinkedList;

@RunWith(ConcurrentParameterized.class)
public class FindAgentTest extends SauceTestBase
{
	public FindAgentTest(String platformName, String platformVersion, String deviceName, String browserName)
	{
		super(platformName, platformVersion, deviceName, browserName);
	}

	@ConcurrentParameterized.Parameters
	public static LinkedList getDevices()
	{
		return new LinkedList() {{
			add(new String[] {"iOS", "11.3", "iPad Pro (12.9 inch) (2nd generation) Simulator", "Safari"});
			add(new String[] {"iOS", "11.4", "iPhone 7 Simulator", "Safari"});
		}};
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

		// WAIT FOR NEW TAB TO LOAD
		wait.until(ExpectedConditions.titleContains("Agent"));

		// CONTEXT AFTER OPENING NEW TAB
		ContextHandler.printContextInfo(driver);
		Assertions.assertThat(driver.getTitle()).isIn(Allstate.FindAgentPage.possibleTitles);

		// SWITCH CONTEXT
		System.out.println("Switch context"); // note switching app context / not window handle
		ContextHandler.switchWebContext(driver);

		// CONTEXT AFTER SWITCHING BACK
		ContextHandler.printContextInfo(driver);
		Assertions.assertThat(driver.getTitle()).isEqualTo(Allstate.HomePage.title);
	}
}