package pages.airports;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Destinations {
	WebDriver driver;
	
	/**
	 * Constructor of class
	 * 
	 * @param driver
	 */
	public Destinations(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	* Method for finding all possible airlines
	* 
	* @param By webElement
	* @return List<WebElement>
	*/
	public List<WebElement> getWebElements(By webElement){
		return driver.findElements(webElement);
	}
}
