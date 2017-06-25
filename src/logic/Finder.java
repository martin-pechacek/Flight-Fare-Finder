package logic;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import org.apache.commons.io.FileUtils;

import pages.airlines.Ryanair;
import pages.airlines.Wizzair;
import utility.ExcelUtils;
import utility.TestReporter;

@Listeners(TestReporter.class)
public class Finder {
	WebDriver driver;
	private static final String CHROME_DRIVER = System.getProperty("user.dir")+"//src//utility//chromedriver.exe";
	File file;
	
	
	@BeforeTest
    public void setup() {
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
		driver = new ChromeDriver();
	}
	
	@Test
	@Parameters({"from", "to", "priceLimit"})
	public void findFlight(String from, String to, int priceLimit) throws Exception{
		int price;
		WebElement searchButton;
		WebDriverWait wait = new WebDriverWait(driver, 30);
		List<WebElement> months;
		
		List<String> airlines = getAirlines(from, to);
		
		for(String airline : airlines){			
			switch(airline.toLowerCase()){
				case("ryanair"):
					Ryanair ryanair = new Ryanair(driver);
					driver.get(ryanair.getWebpage());
					
					Actions action = new Actions(driver);
					
					WebElement fromInput = wait.until((ExpectedConditions.elementToBeClickable(ryanair.getFromInputXpath())));
					action.doubleClick(fromInput).build().perform();
					fromInput.sendKeys(from);
					fromInput.sendKeys(Keys.ENTER);
					
					WebElement toInput = wait.until((ExpectedConditions.elementToBeClickable(ryanair.getToInputXpath())));
					toInput.click();
					toInput.sendKeys(to);
					toInput.sendKeys(Keys.ENTER);					
					
					WebElement allResults = wait.until(ExpectedConditions.elementToBeClickable(ryanair.getAllResults()));
					action.moveToElement(allResults).click().perform();					

					Thread.sleep(1000);
					
					boolean isLoadedResult = driver.findElements(ryanair.getFromInputXpath()).size() > 0;
				
					while(isLoadedResult){						
						allResults.sendKeys(Keys.ENTER);
						Thread.sleep(1000);
						isLoadedResult = driver.findElements(ryanair.getFromInputXpath()).size() > 0;
					}
										
					wait.until(ExpectedConditions.visibilityOfElementLocated(ryanair.getMonths()));
					
					months = driver.findElements(ryanair.getMonths());
					
					price = ryanair.getPrice();
					
					takeScreenshot();
					
					for(int i = 0; i < months.size(); i++){					
						WebElement month = months.get(i);
						month.click();
						
						Thread.sleep(1500);
						
						if(price > ryanair.getPrice()){
							file.delete();
							takeScreenshot();
							price = ryanair.getPrice();
						}
					}					
					
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
					
					searchButton = driver.findElement(wizzair.getSearchButton());
					searchButton.click();
						  		
					WebElement selectBox = wait.until((ExpectedConditions.elementToBeClickable(wizzair.getMonthSelectbox()))); 
					selectBox.click();
					
					price = wizzair.getPrice();
					
					takeScreenshot();
					
					months = driver.findElements(wizzair.getMonthsFromSelectbox());
					
					for(int i=0; i<months.size(); i++){
						wait.until((ExpectedConditions.numberOfElementsToBeLessThan(wizzair.getLoader(), 1))); 
						selectBox.click();
						
						WebElement month = wait.until((ExpectedConditions.elementToBeClickable(months.get(i))));
						month.click();
						
						if(price > wizzair.getPrice()){
							file.delete();
							takeScreenshot();
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
			return true;
		} else {
			return false;
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
	
	/**
	 * Method for taking screenshot.
	 */
	private void takeScreenshot() throws Exception{
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		String filePath = System.getProperty("user.dir")+"\\src\\utility\\screenshot.png";
		
		File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
		
		file = new File(filePath);
		
		FileUtils.copyFile(sourceFile,file);
	}
	
	@AfterTest
	public void teardown(){
		file.delete();
		driver.close();
	}
	  
	
	

}
