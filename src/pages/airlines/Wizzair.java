package pages.airlines;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Wizzair {
	private String link = "https://wizzair.com/en-gb/information-and-services/destinations/timetable";
	WebDriver driver;
	By originInput = By.id("search-departure-station");
	By destinationInput = By.id("search-arrival-station");
	By searchButton = By.cssSelector(".button.button--large.button--block.button--filled");
	By price = By.cssSelector("fare-finder__calendar__monthly-prices__price.fare-finder__calendar__monthly-prices__price--minimum.title.title--2");
	By monthSelectboxFromOrigin = By.xpath("//*[@id='app']/div[3]/div/main/div/div/div[2]/div[2]/div[1]/div[1]/div/select");
	By monthsFromOriginSelectbox = By.xpath("//*[@id='app']/div[3]/div/main/div/div/div[2]/div[2]/div[1]/div[1]/div/select/option");
	
	public Wizzair(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Method that provides link of airline web page
	 * 
	 * @return link
	 */
	public String getWebpage(){
		return link;
	}
	
	/**
	 * Method that provides id of origin input
	 * 
	 * @return originInput
	 */
	public By getOriginInput(){
		return originInput;
	}
	
	/**
	 * Method that provides id of destination input
	 * 
	 * @return destinationInput
	 */
	public By getDestinationInput(){
		return destinationInput;
	}
	
	/**
	 * Method that provides css selector of search button
	 * 
	 * @return searchButton
	 */
	public By getSearchButton(){
		return searchButton;
	}
	
	/**
	 * Method that returns price
	 * 
	 * @return price
	 */
	public String getPrice(){
		String priceString = driver.findElement(price).getText();
		return priceString;
	}
	
	/**
	 * Method that  provides xpath of selectbox with months
	 * 
	 * @return monthSelectbox
	 */
	public By getMonthSelectbox(){
		return monthSelectboxFromOrigin;
	}
	
	/**
	 * Method that  provides xpath of month in selectbox
	 * 
	 * @return months
	 */
	public By getMonthsFromSelectbox(){
		return monthsFromOriginSelectbox;
	}
}
