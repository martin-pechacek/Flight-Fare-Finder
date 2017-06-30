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
	 * @param driver - WebDriver
	 */
	public Destinations(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	* Method for finding all possible airlines from airport
	* 
	* @param By webElement - Selector of web element
	* @return webElements - List with web elements
	*/
	public List<WebElement> getWebElements(By webElement){
		List<WebElement> webElements = driver.findElements(webElement);
		
		return webElements;
	}
}
