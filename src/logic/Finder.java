package logic;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.airlines.Ryanair;
import pages.airlines.Wizzair;
import utility.ExcelUtils;

public class Finder {
	WebDriver driver;
	private static final String CHROME_DRIVER = System.getProperty("user.dir")+"//src//utility//chromedriver.exe";
	
	
	@BeforeTest
    public void setup() {
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
		driver = new ChromeDriver();
	}
	
	@Test
	@Parameters({"from", "to", "priceLimit"})
	public void findFlight(String from, String to, int priceLimit) throws InterruptedException, IOException{
		int price;
		
		List<String> airlines = getAirlines(from, to);
		
		for(String airline : airlines){			
			switch(airline.toLowerCase()){
				case("ryanair"):
					Ryanair ryanair = new Ryanair(driver);
					driver.get(ryanair.getWebpage());
					
					WebElement fromSelectBox = driver.findElement(ryanair.getFromSelectbox());
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click()", fromSelectBox);
					
					WebElement departureCity = driver.findElement(ryanair.getDepartureCity(from));
					departureCity.click();
					
					price =  Integer.parseInt(ryanair.getFlightPrice(to));
					
					assertTrue(controlPrice(price, priceLimit));
					break;
				case("wizz air"):				
					Wizzair wizzair = new Wizzair(driver);
					driver.get(wizzair.getWebpage());
					
					WebElement originInput = driver.findElement(wizzair.getOriginInput());
					originInput.sendKeys(from);
					originInput.sendKeys(Keys.TAB);
					
					WebElement destinationInput = driver.findElement(wizzair.getDestinationInput());
					destinationInput.sendKeys(to);
					destinationInput.sendKeys(Keys.TAB);
					
					WebElement searchButton = driver.findElement(wizzair.getSearchButton());
					searchButton.click();
					
					WebDriverWait wait = new WebDriverWait(driver, 30);			  		
					WebElement selectBox = wait.until((ExpectedConditions.elementToBeClickable(wizzair.getMonthSelectbox()))); 
					selectBox.click();
					
					price = wizzair.getPrice();
					
					List<WebElement> months = driver.findElements(wizzair.getMonthsFromSelectbox());
					
					for(int i=0; i<months.size(); i++){
						wait.until((ExpectedConditions.numberOfElementsToBeLessThan(wizzair.getLoader(), 1))); 
						selectBox.click();
						
						WebElement month = wait.until((ExpectedConditions.elementToBeClickable(months.get(i))));
						month.click();
						
						if(price > wizzair.getPrice()){
							price = wizzair.getPrice();
						}
					}
					
					assertTrue(controlPrice(price, priceLimit));
					
					break;
			}
		}
	}
	
	/**
	 * Method returning true of false value depending on 
	 * price and limit set by user
	 * 
	 * true - price is higher than limit
	 * false - price is lower than limit
	 * 
	 * @param price
	 * @param limit
	 * @return true/false
	 */
	private boolean controlPrice(int price, int limit){
		if(price<=limit){
			return false;
		} else {
			return true;
		}				
	}
	
	/**
	 * Method providing list of airlines which flights from origin city to destinations
	 * 
	 * @param originCity
	 * @param destination
	 * @return airlines
	 * @throws IOException
	 */
	private List<String> getAirlines(String originCity, String destination) throws IOException{
		ArrayList<String> airlines = new ArrayList<>();
		airlines = ExcelUtils.readAirlines(originCity, destination);
		return airlines;
	}
	
	@AfterTest
	public void teardown(){
		driver.close();
	}
	  
	
	

}
