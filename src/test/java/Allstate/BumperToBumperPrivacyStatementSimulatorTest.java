package Allstate;

import AppiumUtil.ContextHandler;
import com.saucelabs.junit.ConcurrentParameterized;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.LinkedList;

@RunWith(ConcurrentParameterized.class)
public class BumperToBumperPrivacyStatementSimulatorTest extends SauceTestBase
{
	@ConcurrentParameterized.Parameters
	public static LinkedList getDevices()
	{
		return new LinkedList() {{
			add(new String[] {"iOS", "11.3", "iPad Pro (12.9 inch) (2nd generation) Simulator", "Safari"});
//			add(new String[] {"iOS", "11.3", "iPad Pro (9.7 inch) Simulator", "Safari"});
//			add(new String[] {"iOS", "11.3", "iPhone 7 Simulator", "Safari"});
		}};
	}

	public BumperToBumperPrivacyStatementSimulatorTest(String platformName, String platformVersion, String deviceName, String browserName)
	{
		super(platformName, platformVersion, deviceName, browserName);
	}


	@Test
	public void test_open_privacy_statement_in_new_tab()
	{
		// OPEN BUMPER TO BUMPER PAGE
		open(BumperToBumperPage.url);

		// INITIAL CONTEXT
		waitForReadyState();
		ContextHandler.printContextInfo(driver);

		// CLICK ON PRIVACY STATEMENT LINK
		click(BumperToBumperPage.PrivacyStatementLink);

		// CHECK CONTEXT
		waitForReadyState();
		ContextHandler.printContextInfo(driver);

		// CLICK NY CUSTOMERS LINK
		click(PrivacyStatementPage.AllstateLifeInsuranceCompanyOfNYCustomers);

		// CHECK CONTEXT
		waitForReadyState();
		ContextHandler.printContextInfo(driver);

		// SWITCH CONTEXT
		ContextHandler.switchWebContext(driver);

		// CHECK CONTEXT
		waitForReadyState();
		ContextHandler.printContextInfo(driver);
	}

	public ExpectedCondition<WebElement> clickable(By locator)
	{
		System.out.println("when clickable: " + locator);
		System.out.println("readystate: " + getReadyState());

		return ExpectedConditions.elementToBeClickable(locator);
	}

	public ExpectedCondition<WebElement> visible(By locator)
	{
		System.out.println("when visible: " + locator);
		return ExpectedConditions.visibilityOfElementLocated(locator);
	}

	public ExpectedCondition<WebElement> present(By locator)
	{
		System.out.println("when present: " + locator);
		return ExpectedConditions.presenceOfElementLocated(locator);
	}

	public ExpectedCondition<WebElement> clickable(WebElement element)
	{
		System.out.println("when clickable: " + element);
		return ExpectedConditions.elementToBeClickable(element);
	}

	public ExpectedCondition<WebElement> visible(WebElement element)
	{
		System.out.println("when visible: " + element);
		return ExpectedConditions.visibilityOf(element);
	}


	public void click(By locator)
	{
		WebElement element = wait.until(clickable(locator));
		System.out.println("click: " + element);
		System.out.println("readystate: " + getReadyState());
		element.click();
	}

	public void open(String url)
	{
		System.out.println("open " + url);
		driver.get(url);
	}

	public static void sleep(int seconds)
	{
		try
		{
			Thread.sleep(1000 * seconds);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public String getReadyState()
	{
		return driver.executeScript("return document.readyState;").toString();
	}

	public void waitForReadyState()
	{
		for (int i=0; i<10; i++)
		{
			String readyState = getReadyState();
			System.out.println("readyState: " + readyState);

			if (readyState.equals("complete"))
			{
				return;
			}
			sleep(5);
		}
	}
}
