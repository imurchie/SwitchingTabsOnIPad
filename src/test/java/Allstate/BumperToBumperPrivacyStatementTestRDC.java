package Allstate;

import AppiumUtil.ContextHandler;
import com.saucelabs.junit.ConcurrentParameterized;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.LinkedList;

@RunWith(ConcurrentParameterized.class)
public class BumperToBumperPrivacyStatementTestRDC extends SauceRDCTestBase
{
	@ConcurrentParameterized.Parameters
	public static LinkedList getDevices()
	{
		return new LinkedList() {{
			add(new String[] {"iOS", "11.4.1", "iPad Pro 12.9", "Safari"});
			add(new String[] {"iOS", "11.4.1", "iPad Pro 9.7", "Safari"});
			add(new String[] {"iOS", "11.4.1", "iPhone 7", "Safari"});
		}};
	}

	public BumperToBumperPrivacyStatementTestRDC(String platformName, String platformVersion, String deviceName, String browserName)
	{
		super(platformName, platformVersion, deviceName, browserName);
	}


	@Test
	public void test_open_privacy_statement_in_new_tab()
	{
		// OPEN BUMPER TO BUMPER PAGE
		System.out.println("open " + BumperToBumperPage.url);
		driver.get(BumperToBumperPage.url);

		// INITIAL CONTEXT
		ContextHandler.printContextInfo(driver);

		// CLICK ON PRIVACY STATEMENT LINK
		System.out.println("click " + BumperToBumperPage.PrivacyStatementLink);
		wait.until(ExpectedConditions.elementToBeClickable(BumperToBumperPage.PrivacyStatementLink)).click();

		// CHECK CONTEXT
		ContextHandler.printContextInfo(driver);

		wait.until(ExpectedConditions.elementToBeClickable(PrivacyStatementPage.AllstateLifeInsuranceCompanyOfNYCustomers)).click();

		// CHECK CONTEXT
		ContextHandler.printContextInfo(driver);

		// SWItCH CONTEXT
		ContextHandler.switchWebContext(driver);

		// CHECK CONTEXT
		ContextHandler.printContextInfo(driver);

	}

	public static class BumperToBumperPage
	{
		public static String url = "https://www-stest.allstate.com/anon/bumpertobumper/default.aspx";
		public static String title = "Allstate Bumper-to-Bumper Basics | Auto Insurance Guide";

		public static By PrivacyStatementLink = By.linkText("Privacy Statement");

		public static By ECCMonitor = By.xpath("//span[contains(text(),'ECC Monitor')]");
	}

	public static class PrivacyStatementPage
	{
		public static String url = "https://www-stest.allstate.com/about/privacy-statement.aspx";
		public static String title = "Allstate.com - Learn More About Allstate Insurance Company";

		public static By AllstateLifeInsuranceCompanyOfNYCustomers = By.linkText("Allstate Life Insurance Company of NY Customers.");

		public static By ECCMonitor = By.xpath("//span[contains(text(),'ECC Monitor')]");
	}

	public static class NewYorkDomesticViolenceNoticePage
	{
		public static String url = "https://www-stest.allstate.com/about/ny-domestic-violence-notice.aspx";
		public static String title = "New York Domestic Violence Notice - Allstate Insurance";

		public static By heading = By.tagName("h1");
		public static String headingText = "Important Notice For Victims Of Domestic Violence In New York";

		public static By ECCMonitor = By.xpath("//span[contains(text(),'ECC Monitor')]");
	}
}