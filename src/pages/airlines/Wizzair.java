package pages.airlines;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	 * Method that returns price. If flights are not planned yet, method will set price as 1 000 000
	 * 
	 * @return price
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
	
	/**
	 * Method that provides xpath of loading icon
	 * 
	 * @return loader
	 */
	public By getLoader(){
		return loader;
	}
}
