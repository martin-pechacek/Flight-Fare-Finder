package pages.airports;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PragueAirport extends Destinations {
	WebDriver driver;
	By Destinations = By.xpath("//div[@class='aero_block']/span[1]");
	By Country = By.xpath("//div[@class='aero_block']/span[2]");
	By Airline = By.xpath("//div[@class='aero_block']/span[3]");
	String airport = "Prague";
	private String link = "http://www.prg.aero/en/flight-info/airlines-and-destinations/";

	/**
	 * Class constructor
	 * 
	 * @param driver
	 */
	public PragueAirport(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Method that provides xPath of destinations
	 * 
	 * @return destinations xpath
	 */
	public By getDestinationsXpath(){
		return Destinations;
	}
	
	/**
	 * Method that provides xPath of countries
	 * 
	 * @return countries xpath 
	 */
	public By getCountriesXpath(){
		return Country;
	}
	
	/**
	 * Method that provides xPath of all airlines
	 * 
	 * @return airlines xpath
	 */
	public By getAirlinesXpath(){
		return Airline;
	}
	
	/**
	 * Method that provides name of airport
	 * 
	 * @return airport
	 */
	public String getAirport(){
		return airport;
	}
	
	/**
	 * Method that provides link of airport web page where are all possible destinations 
	 * 
	 * @return link
	 */
	public String getWebpage(){
		return link;
	}
}
