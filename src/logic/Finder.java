package logic;

import static org.testng.Assert.assertFalse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.airlines.Ryanair;
import pages.airlines.Wizzair;

public class Finder {
	WebDriver driver;
	private static final String CHROME_DRIVER = System.getProperty("user.dir")+"//src//utility//chromedriver.exe";
	
	
	@BeforeTest
    public void setup() {
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
		driver = new ChromeDriver();
	}
	
	@Test
	public void findFlight(){
		String airline = "wizz";    //HARDCODED!!!!
		String from = "prague";     //HARDCODED!!!!
		String to = "Reykjavik";       //HARDCODED!!!!
		int limit = 400;            //HARDCODED!!!!
		
		switch(airline){
			case("ryanair"):
				Ryanair ryanair = new Ryanair(driver);
				driver.get(ryanair.getWebpage());
				
				WebElement fromSelectBox = driver.findElement(ryanair.getFromSelectbox());
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click()", fromSelectBox);
				
				WebElement departureCity = driver.findElement(ryanair.getDepartureCity(from));
				departureCity.click();
				
				int price =  Integer.parseInt(ryanair.getFlightPrice(to));
				
				if(price<=limit){
					assertFalse(true);
				}				
				break;
			case("wizz"):
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
				
				WebDriverWait wait = new WebDriverWait(driver, 5);			  		
				WebElement selectBox = wait.until((ExpectedConditions.elementToBeClickable(wizzair.getMonthSelectbox()))); 
				selectBox.click();
				
				List<WebElement> months = driver.findElements(wizzair.getMonthsFromSelectbox());
				
				for(int i=0; i<12; i++){
					driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
					selectBox.click();
					
					WebElement month = wait.until((ExpectedConditions.elementToBeClickable(months.get(i+1))));
					month.click();
				}
		}
	}
	
	@AfterTest
	public void teardown(){
		driver.close();
	}
	  
	
	

}
