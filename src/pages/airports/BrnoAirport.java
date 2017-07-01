package pages.airports;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BrnoAirport extends Destinations{

	By Arrivals = By.xpath("//label[@class='checkbox_on']");
	By SearchButton = By.cssSelector("a.submit");
	By Interval1Month = By.xpath("//select[@name='interval']/option[@value='30']");
	By Destinations = By.xpath("//table/tbody/tr/td[6]");
	By Airlines = By.xpath("//table/tbody/tr/td[5]");
	private String link = "http://www.brno-airport.cz/en/flight-information/flight-search/";
	String airport = "Brno";
	
	/**
	 * Class constructor
	 * 
	 * @param driver - WebDriver
	 */
	public BrnoAirport(WebDriver driver){
		super(driver);
	}
	
	/**
	 * Method providing xpath of arrival checkbox in the
	 * form for finding flights
	 * 
	 * @return arrivals - Xpath of arrival checkbox
	 */
	public By getArrivalsXpath(){
		return Arrivals;
	}
	
	/**
	 * Method providing xpath of search button in the
	 * form for finding flights
	 * 
	 * @return SearchButton - Xpath of search button
	 */
	public By getSearchButtonXpath(){
		return SearchButton;
	}
	
	/**
	 * Method providing xpath of Interval +1 Month from chosen day in selectbox
	 * 
	 * @return Interval1Month - Xpath of Interval +1 Month from chosen day in selectbox
	 */
	public By getInterval1MonthXpath(){
		return Interval1Month;
	}
	
	/**
	 * Method providing xpath of destinations
	 * 
	 * @return Destinations - Xpath of destinations
	 */
	public By getDestinationsXpath(){
		return Destinations;
	}
	
	/**
	 * Method providing xpath of airlines
	 * 
	 * @return Destinations - Xpath of airlines
	 */
	public By getAirlinesXpath(){
		return Airlines;
	}
	
	/**
	 * Method that provides link of airport web page where are all possible destinations 
	 * 
	 * @return link - Link of airport web page where are all possible destinations 
	 */
	public String getWebpage(){
		return link;
	}
	
}
