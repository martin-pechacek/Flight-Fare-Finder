package logic;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
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
public class Finder{
	WebDriver driver;
	private static final String CHROME_DRIVER_WIN = "src//utility//chromedriver.exe";
	private static final String CHROME_DRIVER_LINUX = "//usr//local//bin//chromedriver";
	File file;
	int price;
	WebElement searchButton;
	List<WebElement> months;
	WebDriverWait wait;
	List<String> airlines;
	
	/**
	 * Test that runs after testNG is initialized and 
	 * sets WebDriver and WebDriverWait for Windows 
	 * and Linux. 
	 * 
	 * Also loads all possible airlines which
	 * flight between two cities set in test parameters
	 * 
	 * @param from - City of Departure
	 * @param to - City of arrival
	 * @throws IOException
	 */
	@BeforeTest
	@Parameters({"from", "to"})
    public void setup(String from, String to) throws IOException {
	    String system = System.getProperty("os.name");   
	    airlines = getAirlines(from, to);
	    
	    if(system.toLowerCase().contains("windows")){
	    	System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_WIN);
	    } 
	    if(system.toLowerCase().contains("linux")) {
	    	System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_LINUX);    	
	    }
	    
	    driver = new ChromeDriver();
	    wait = new WebDriverWait(driver, 30);
	}
	
	/**
	 * Method that runs before every test method 
	 * to decide if method should be run or not.
	 * If method name does not contain airline name flying
	 * between two cities set in test parameters then skip test
	 * 
	 * @param methodName - Name of test method
	 */
	@BeforeMethod
    public void checkAirline(Method methodName){
		if(!doesFlight(methodName)){
			driver.close();
			throw new SkipException("Does not flight");
		}
    }
	
	/**
	 * Method for flight fare control of flights provided by ryanair
	 * 
	 * @param from - City of departure
	 * @param to - City of arrival
	 * @param priceLimit - Price limit set by user
	 * @throws Exception
	 */
	@Test
	@Parameters({"from", "to", "priceLimit"})
	public void ryanair(String from, String to, int priceLimit, ITestContext context) throws Exception{		
		setAirlineContext(context, "Ryanair");
		Ryanair ryanair = new Ryanair(driver);
		driver.get(ryanair.getWebpage());
		driver.manage().window().maximize();
		
		
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
				takeScreenshot();
				price = ryanair.getPrice();
			}
		}
		
		setPriceContext(context);
		
		assertTrue(controlPrice(price, priceLimit));
	}

	/**
	 * Method for flight fare control of flights provided by wizzair
	 * 
	 * @param from - City of departure
	 * @param to - City of arrival
	 * @param priceLimit - Price limit set by user
	 * @throws Exception
	 */
	@Test
	@Parameters({"from", "to", "priceLimit"})
	public void wizzair(String from, String to, int priceLimit, ITestContext context) throws Exception{
		setAirlineContext(context, "Wizz Air");
		Wizzair wizzair = new Wizzair(driver);
		driver.get(wizzair.getWebpage());
		driver.manage().window().maximize();
		
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
				takeScreenshot();
				price = wizzair.getPrice();
			}
		}
		
		setPriceContext(context);
		
		assertTrue(controlPrice(price, priceLimit));
	}
	
	/**
	 * Method returning true of false value depending on 
	 * price and limit set by user
	 * 
	 * 
	 * @param price - Price found on page of airline
	 * @param limit - Price limit set by user
	 * @return If user price is lower than returns true
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
	 * @param originCity - City of departure
	 * @param destination - City of arrival
	 * @return airlines - List of all airlines flying between those two cities
	 * @throws IOException
	 */
	private List<String> getAirlines(String originCity, String destination) throws IOException{
		ArrayList<String> airlines = new ArrayList<>();
		airlines = ExcelUtils.readAirlines(originCity, destination);
		return airlines;
	}
	
	/**
	 * Method for taking screenshot
	 */
	private void takeScreenshot() throws Exception{
		if(file!=null){
			file.delete();
		}
		
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		String filePath = System.getProperty("user.dir")+"\\src\\utility\\screenshot.png";
		
		File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
		
		file = new File(filePath);
		
		FileUtils.copyFile(sourceFile,file);
	}
	
	/**
	 * Method for decision if airline provides flight or not
	 * 
	 * @param methodName - Name of method(airline)
	 * @return True if airline provides flight
	 */
	private boolean doesFlight(Method methodName){
		String method = methodName.getName();
		
		for(String airline : airlines){
			airline = airline.replaceAll("\\s+", "");
			
			if(method.contains(airline.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method for setting test context for price (used in mailer)
	 * 
	 * @param context - ITestContext(set natively)
	 */
	private void setPriceContext(ITestContext context) {
		context.setAttribute("price", price);	
	}
	
	/**
	 * Method for setting test context for airline name (used in mailer)
	 * 
	 * @param context - ITestContext(set natively on test start)
	 * @param airline - Name of airline
	 */
	private void setAirlineContext(ITestContext context, String airline) {
		context.setAttribute("airline", airline);		
	}
	
	/**
	 * Method which runs when test ends that closes driver
	 * and deletes taken screenshot
	 */
	@AfterSuite
	public void teardown(){
		file.delete();
		driver.close();
	}
}
