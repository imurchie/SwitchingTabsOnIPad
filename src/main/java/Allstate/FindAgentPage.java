package Allstate;

import java.util.Arrays;
import java.util.List;

public class FindAgentPage
{
	public static String url = "https://agents.allstate.com/locator.html?zip=&intcid=%2Fhome%2Fhome.aspx%7CMainNav%7Cfindagent";
	public static String title = "Find Insurance Agents Near You | Allstate";
	public static String title2 = "Insurance Agent Locator | Allstate";


	public static List<String> possibleTitles = Arrays.asList(title,title2);
}
