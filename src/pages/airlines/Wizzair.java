package pages.airlines;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Wizzair {
	private String link = "https://wizzair.com/en-gb/information-and-services/destinations/timetable";
	WebDriver driver;
	By originInput = By.id("search-departure-station");
	By destinationInput = By.id("search-arrival-station");
	By searchButton = By.cssSelector(".button.button--large.button--block.button--filled");
	By priceSelectorFromOrigin = By.xpath("//*[@id='app']/div[3]/div/main/div/div/div[2]/div[2]/div[1]/div[2]/div[1]/div[1]");
	By monthSelectboxFromOrigin = By.xpath("//*[@id='app']/div[3]/div/main/div/div/div[2]/div[2]/div[1]/div[1]/div/select");
	By monthsFromOriginSelectbox = By.xpath("//*[@id='app']/div[3]/div/main/div/div/div[2]/div[2]/div[1]/div[1]/div/select/option");
	By loader = By.xpath("//div[@class='fare-finder__calendar box loader-combined']");
	
	/**
	 * Class constructor
	 * 
	 * @param driver - WebDriver
	 */
	public Wizzair(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Method providing link of airline web page
	 * 
	 * @return link - Airline web page with form for finding flight
	 */
	public String getWebpage(){
		return link;
	}
	
	/**
	 * Method providing id of departure input field
	 * in the form for finding flights
	 * 
	 * @return originInput - Id of departure input field
	 */
	public By getOriginInput(){
		return originInput;
	}
	
	/**
	 * Method providing id of destination input field
	 * in the form for finding flights
	 * 
	 * @return destinationInput - Id of destination input field
	 */
	public By getDestinationInput(){
		return destinationInput;
	}
	
	/**
	 * Method providing css selector of search button
	 * in the form for finding flights
	 * 
	 * @return searchButton - Css selector of search button
	 */
	public By getSearchButton(){
		return searchButton;
	}
	
	/**
	 * Method returning price. 
	 * If flights are not planned yet, method will set price 1 000 000
	 * 
	 * @return price - Found lowest price in the month
	 */
	public int getPrice(){
		boolean isPriceDisplayed = driver.findElements(priceSelectorFromOrigin).size() > 0;
		int price;
		
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		
		if(isPriceDisplayed){
			String priceString = driver.findElement(priceSelectorFromOrigin).getText();			
			priceString = priceString.replaceAll(",", "").substring(2);			
			price = Integer.parseInt(priceString);	
		} else {
			price = 1000000;
		}		
		return price;
	}
	
	/**
	 * Method providing xpath of selectbox with months
	 * 
	 * @return monthSelectbox - Xpath of selectbox with months
	 */
		public By getMonthSelectbox(){
		return monthSelectboxFromOrigin;
	}
	
	/**
	 * Method providing xpath of month in selectbox
	 * 
	 * @return months - Xpath of month in selectbox
	 */
	public By getMonthsFromSelectbox(){
		return monthsFromOriginSelectbox;
	}
	
	/**
	 * Method providing xpath of loading icon
	 * 
	 * @return loader - Xpath of loading icon
	 */
	public By getLoader(){
		return loader;
	}
}
