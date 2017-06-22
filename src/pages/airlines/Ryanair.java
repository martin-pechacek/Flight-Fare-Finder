package pages.airlines;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import javax.swing.text.DateFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.sf.cglib.core.Local;

public class Ryanair {
	private String link = "https://www.ryanair.com/gb/en/cheap-flight-destinations";
	WebDriver driver;
	By fromInput = By.xpath("//*[@id='route-map-widget']/searchbox-widget/div/div/div[1]/div[1]/input");
	By toInput = By.xpath("//*[@id='route-map-widget']/searchbox-widget/div/div/div[1]/div[2]/input");
	By nextMonth = By.xpath("//*[@id='row-dates-pax']/div[1]/div/div[1]/div/div[3]/div/div/div[2]/popup-content/core-datepicker/div/div[1]/ul/li[2]/ul[2]/li[6]/span");
	By allResults = By.xpath("/html/body/div[2]/main/div/farefinder-card/div/div/farefinder-card-details/div[2]/div[3]/div[2]/button");
	By months = By.xpath("/html/body/div[2]/main/div/div/div[1]/div[2]/div/div/div[2]/div/ul/li");
	By priceLabel = By.xpath("/html/body/div[2]/main/div/div/div[1]/div[4]/div/div[1]/div[5]/span");
	
	/**
	 * Class constructor
	 * 
	 * @param driver
	 */
	public Ryanair(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Method providing link of airline web page
	 * 
	 * @return link
	 */
	public String getWebpage(){
		return link;
	}
	
	
	/**
	 * Method providing xpath of input field for origin city
	 * 
	 * @return fromInput
	 */
	public By getFromInputXpath(){
		return fromInput;
	}
	
	/**
	 * Method providing xpath of input field for destination city
	 * 
	 * @return toInput
	 */
	public By getToInputXpath(){
		return toInput;
	}
	
	/**
	 * Method providing xpath of first day of next month in date picker
	 * 
	 * @return nextMonth
	 */
	public By getNextMonth(){
		return nextMonth;
	}
	
	/**
	 * Method providing xpath of search button
	 * 
	 * @return nextMonth
	 */
	public By getAllResults(){
		return allResults;
	}

	/**
	 * Method providing xpath of months
	 * 
	 * @return months
	 */
	 public By getMonths(){
	 	return months;
	 }
	 
	 /**
	  * Method providing xpath of price label
	  * 
	  * @return priceLabel
	  */
	 public By getPriceLabelXpath(){
		 return priceLabel;
	 }
	 
	 /**
	  * Method providing lowest flight fare in month
	  * 
	  * @return price
	  */
	 public int getPrice(){		  
		 int price;		
		 driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
			
		 
		 WebElement priceElement = driver.findElement(priceLabel);
		 String priceLabel = priceElement.getText().replaceAll(",", "").substring(3);
		 
		 price = Integer.parseInt(priceLabel); 
		 		 
		 return price;
	 }
	
	/**
	 * Method providing xpath of flight price
	 * 
	 */
	/*public String getFlightPrice(String destination){
		String circleXpath = "//div[@class='circle']";
		int count = driver.findElements(By.xpath(circleXpath)).size();
		String capitalizedDestination = destination.substring(0, 1).toUpperCase() + destination.substring(1);
		
		WebElement circle = driver.findElement(By.xpath(circleXpath+"["+ count +"]")); 
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click()", circle);
		
		By priceXpath = By.xpath("//div[p[text()[contains(.,'"+capitalizedDestination+"')]]]/following-sibling::div/span/span[2]");
		
		String price = driver.findElement(priceXpath).getAttribute("innerHTML");
		
		return price;
	}*/
}
