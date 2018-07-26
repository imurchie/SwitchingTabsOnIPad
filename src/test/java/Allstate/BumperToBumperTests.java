package Allstate;

import org.junit.Test;

import java.net.MalformedURLException;

public class BumperToBumperTests extends BumperToBumperTestBase
{

	@Test
	public void shouldOpenPrivacyStatement_Local() throws MalformedURLException
	{
		useLocalIpadSimulator();
		executeSteps();
	}

	@Test
	public void shouldOpenPrivacyStatement_Sauce() throws MalformedURLException
	{
		useSauceIpadSimulator();
		executeSteps();
	}

	@Test
	public void shouldOpenPrivacyStatement_RDC() throws MalformedURLException
	{
		useSauceIpadRDC();
		executeSteps();
	}
}
