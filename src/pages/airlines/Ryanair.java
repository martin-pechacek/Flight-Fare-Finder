package pages.airlines;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	 * @param driver - WebDriver
	 */
	public Ryanair(WebDriver driver){
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
	 * Method providing xpath of input field for origin city
	 * 
	 * @return fromInput - Xpath of input field for departure
	 * city in form for finding flights
	 */
	public By getFromInputXpath(){
		return fromInput;
	}
	
	/**
	 * Method providing xpath of input field for destination city
	 * 
	 * @return toInput - Xpath of input field for arrival
	 * city in form for finding flights
	 */
	public By getToInputXpath(){
		return toInput;
	}
	
	/**
	 * Method providing xpath of link submitting form for finding flight
	 * 
	 * @return allResults - Xpath of link submitting form for finding flight
	 */
	public By getAllResults(){
		return allResults;
	}

	/**
	 * Method providing xpath of months clickable component 
	 * on page with found flight
	 * 
	 * @return months - Xpath of months clickable component
	 */
	 public By getMonths(){
	 	return months;
	 }
	 
	 /**
	  * Method providing xpath of flight price label
	  * 
	  * @return priceLabel - Xpath of flight price label
	  */
	 public By getPriceLabelXpath(){
		 return priceLabel;
	 }
	 
	 /**
	  * Method providing lowest flight fare in month
	  * 
	  * @return price - Lower flight fare in the month
	  */
	 public int getPrice(){		  
		 int price;		
		 driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
			
		 
		 WebElement priceElement = driver.findElement(priceLabel);
		 
		 String priceText = priceElement.getText();
		 
		 //control if flights exists. If not set price 1000000
		 if(!priceText.equalsIgnoreCase("")){
			 String priceLabel = priceElement.getText().replaceAll(",", "").substring(3);			 
			 price = Integer.parseInt(priceLabel);  
		 } else {
			 price = 1000000;
		 }
		 		 
		 return price;
	 }
}
