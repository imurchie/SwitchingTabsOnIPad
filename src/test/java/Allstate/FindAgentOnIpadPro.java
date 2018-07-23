package Allstate;

import AppiumUtil.ContextHandler;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FindAgentOnIpadPro extends SauceTestBase
{
	public static String platformName = "iOS";
	public static String platformVersion = "11.3";
	public static String deviceName = "iPad Pro (12.9 inch) (2nd generation) Simulator";
	public static String browserName = "Safari";
	public static String appiumUrl = SAUCE_URL;

	public FindAgentOnIpadPro()
	{
		super(platformName, platformVersion, deviceName, browserName);
	}

	@Test
	public void switchTabsBySwitchingContext()
	{
		// OPEN HOME PAGE
		System.out.println("Open " + Allstate.HomePage.url);
		driver.get(Allstate.HomePage.url);

		// INITIAL CONTEXT
		ContextHandler.printContextInfo(driver);

		// CLICK LINK THAT OPENS IN A NEW TAB
		System.out.println("Click " + Allstate.HomePage.findAgentLink);
		wait.until(ExpectedConditions.elementToBeClickable(Allstate.HomePage.findAgentLink)).click();

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
