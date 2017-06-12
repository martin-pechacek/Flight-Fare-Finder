package pages.airlines;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Ryanair {
	private String link = "https://www.ryanair.com/gb/en/";
	WebDriver driver;
	By FromSelectbox = By.xpath("//span[contains(@ng-click,'vmAirportSelector')]");	
	
	/**
	 * Class constructor
	 * 
	 * @param driver
	 */
	public Ryanair(WebDriver driver){
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
	
	/*
	 * Method that provides xpath of selectbox for departure airport
	 */
	public By getFromSelectbox(){
		return FromSelectbox;
	}
	
	/**
	 * Method that provides xpath of departure city in the selectbox
	 * 
	 * @param city
	 * @return city xpath
	 */
	public By getDepartureCity(String city){
		String capitalizedCity = city.substring(0, 1).toUpperCase() + city.substring(1);
		By departureAirport = By.xpath("//div[text()[contains(.,'"+ capitalizedCity +"')]]/ancestor::div[contains(@class,'core-list-ref')]");
		return departureAirport;
	}
	
	/**
	 * Method providing xpath of flight price
	 * 
	 */
	public String getFlightPrice(String destination){
		String circleXpath = "//div[@class='circle']";
		int count = driver.findElements(By.xpath(circleXpath)).size();
		String capitalizedDestination = destination.substring(0, 1).toUpperCase() + destination.substring(1);
		
		WebElement circle = driver.findElement(By.xpath(circleXpath+"["+ count +"]")); 
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click()", circle);
		
		By priceXpath = By.xpath("//div[p[text()[contains(.,'"+capitalizedDestination+"')]]]/following-sibling::div/span/span[2]");
		
		String price = driver.findElement(priceXpath).getAttribute("innerHTML");
		
		return price;
	}
}
