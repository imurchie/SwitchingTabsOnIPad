package Allstate;

import org.openqa.selenium.By;

public class NewYorkDomesticViolenceNoticePage
{
	public static String url = "https://www-stest.allstate.com/about/ny-domestic-violence-notice.aspx";
	public static String title = "New York Domestic Violence Notice - Allstate Insurance";

	public static By heading = By.tagName("h1");
	public static String headingText = "Important Notice For Victims Of Domestic Violence In New York";

	public static By ECCMonitor = By.xpath("//span[contains(text(),'ECC Monitor')]");
}
