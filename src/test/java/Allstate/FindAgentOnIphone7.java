package Allstate;

import AppiumUtil.ContextHandler;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FindAgentOnIphone7 extends SauceTestBase
{
	public FindAgentOnIphone7()
	{
		super("iOS", "11.3", "iPhone 7 Simulator", "Safari");
	}

	@Test
	public void switchTabsBySwitchingContext()
	{
		// OPEN HOME PAGE
		System.out.println("Open " + Allstate.HomePage.url);
		driver.get(Allstate.HomePage.url);

		// INITIAL CONTEXT
		ContextHandler.printContextInfo(driver);

		// CLICK ON FIND A NEW AGENT LINK
		wait.until(ExpectedConditions.elementToBeClickable(HomePage.SmallDisplay.menuButton)).click();
		wait.until(ExpectedConditions.elementToBeClickable(HomePage.SmallDisplay.findALocalAgentLink)).click();

//		Allstate.HomePage.findAgent(driver);
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
