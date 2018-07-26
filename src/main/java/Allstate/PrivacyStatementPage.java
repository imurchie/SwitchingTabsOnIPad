package Allstate;

import org.openqa.selenium.By;

public  class PrivacyStatementPage
{
	public static String url = "https://www-stest.allstate.com/about/privacy-statement.aspx";
	public static String title = "Allstate.com - Learn More About Allstate Insurance Company";

//	public static By AllstateLifeInsuranceCompanyOfNYCustomers = By.linkText("Allstate Life Insurance Company of NY Customers.");
	public static By AllstateLifeInsuranceCompanyOfNYCustomers = By.xpath("//a[@href='/about/NY-domestic-violence-notice.aspx']");


	public static By ECCMonitor = By.xpath("//span[contains(text(),'ECC Monitor')]");
}