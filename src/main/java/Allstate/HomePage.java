package Allstate;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage
{
	public static String url =  "https://www-stest.allstate.com/";
	public static String title = "Auto Insurance Quotes - Car Insurance | Allstate Online Quote";
	public static By findAgentLink = By.linkText("Find An Agent");

	public static class SmallDisplay
	{
		public static By menuButton = By.id("slide-mobile-navbar");
		public static By findALocalAgentLink = By.linkText("Find A Local Agent");
	}

	public static void findAgent(AppiumDriver driver)
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);

		if (driver.findElement(findAgentLink).isDisplayed())
		{
			wait.until(ExpectedConditions.elementToBeClickable(findAgentLink));
		}
		else if (driver.findElement(SmallDisplay.menuButton).isDisplayed())
		{
			wait.until(ExpectedConditions.elementToBeClickable(SmallDisplay.menuButton)).click();
			wait.until(ExpectedConditions.elementToBeClickable(SmallDisplay.findALocalAgentLink)).click();
		}
	}
}
