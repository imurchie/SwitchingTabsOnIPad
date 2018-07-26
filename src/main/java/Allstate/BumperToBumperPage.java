package Allstate;

import org.openqa.selenium.By;

public class BumperToBumperPage
{
	public static String url = "https://www-stest.allstate.com/anon/bumpertobumper/default.aspx";
	public static String title = "Allstate Bumper-to-Bumper Basics | Auto Insurance Guide";

//	public static By PrivacyStatementLink = By.linkText("Privacy Statement");
	public static By PrivacyStatementLink = By.xpath("//div[@class='footer']/div[@class='row first']/ul[@class='left']/li[1]/a");
//	public static By PrivacyStatementLink = By.xpath("//a[@href='/about/privacy-statement.aspx']");

	public static By ECCMonitor = By.xpath("//span[contains(text(),'ECC Monitor')]");
}
