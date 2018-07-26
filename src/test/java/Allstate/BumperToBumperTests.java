package Allstate;

import org.junit.Test;

import java.net.MalformedURLException;

public class BumperToBumperTests extends BumperToBumperTestBase
{

	@Test
	public void shouldOpenPrivacyStatement_Local() throws MalformedURLException
	{
		useLocalIpadSimulator();
		openTabByClickingAllowInNativeContext();
	}

	@Test
	public void shouldOpenPrivacyStatement_Sauce() throws MalformedURLException
	{
		useSauceIpadSimulator();
		openTabByClickingAllowInNativeContext();
	}

	@Test
	public void shouldOpenPrivacyStatement_RDC() throws MalformedURLException
	{
		useSauceIpadRDC();
		openTabByClickingAllowInNativeContext();
	}
}
