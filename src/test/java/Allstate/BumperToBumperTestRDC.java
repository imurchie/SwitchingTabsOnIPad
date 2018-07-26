package Allstate;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;

public class BumperToBumperTestRDC extends BumperToBumperTestBase
{
	@Before
	public void setup() throws MalformedURLException
	{
		useSauceIpadSimulator();
	}

	@Test
	public void shouldOpenPrivacyStatementInNewTab()
	{
		openTabByClickingAllowInNativeContext();
	}
}
