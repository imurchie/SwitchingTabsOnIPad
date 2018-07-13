package AppiumUtil;

import io.appium.java_client.AppiumDriver;

import java.util.Set;

public class ContextHandler
{
	public static void switchWebContext(AppiumDriver driver)
	{
		String newContext = getNewWebContext(driver);

		if (newContext != null)
		{
			System.out.println("switching to new context: " + newContext);
			driver.context(newContext);
		}
	}

	// This assumes there are only two contexts
	public static String getNewWebContext(AppiumDriver driver)
	{
		String currentContext = driver.getContext();
		Set<String> contextHandles = driver.getContextHandles();

		for (String contextHandle : contextHandles)
		{
			if (contextHandle.startsWith("WEBVIEW") && ! contextHandle.equals(currentContext))
			{
				return contextHandle;
			}
		}

		return null;
	}

	public static void printContextInfo(AppiumDriver driver)
	{
		System.out.println("------");
		System.out.println("CONTEXT HANDLES: " + driver.getContextHandles());
		System.out.println("CURRENT CONTEXT: " + driver.getContext());
		System.out.println("WINDOW HANDLES: " + driver.getWindowHandle());
		System.out.println("CURRENT WINDOW: " + driver.getWindowHandle());
		System.out.println("TITLE: " + driver.getTitle());
		System.out.println("URL: " + driver.getCurrentUrl());
		System.out.println("------");
	}
}
