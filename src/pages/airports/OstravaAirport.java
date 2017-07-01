package pages.airports;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OstravaAirport extends Destinations {
	
	private String link = "http://www.airport-ostrava.cz/en/page-timetable/";
	By DeparturesRadioButton = By.xpath("//form[@class='jqtransform jqtransformdone']/table/tbody/tr[1]/td[2]/div[1]//a");
	By IntervalSelectbox = By.xpath("//a[@class='jqTransformSelectOpen']");
	By Interval1Month = By.xpath("//*[@id='obsah']/div[1]/div[3]/div[2]/form/table/tbody/tr[2]/td[4]/div/ul/li[3]/a");
	By SearchButton = By.xpath("//button[@name='send']");
	By Airlines = By.xpath("//*[@id='obsah']/div[1]/div[6]/div/div[1]/div/table/tbody/tr");
	By Airline = By.xpath("//*[@id='tab-1']/table/tbody/tr/td[4]");
	By Destination = By.xpath("//*[@id='tab-1']/table/tbody/tr/td[5]");
	HashMap<String, String> airlines = new HashMap<String, String>();
	
	/**
	 * Class constructor
	 * 
	 * @param driver - WebElement
	 */
	public OstravaAirport(WebDriver driver){
		super(driver);
	}
	
	/**
	 * Method that provides link of airport web page where are all possible destinations 
	 * 
	 * @return link - Link of airport web page where are all possible destinations 
	 */
	public String getWebpage(){
		return link;
	}
	
	/**
	 * Method providing xpath of departures radio button
	 * in the form for finding flights
	 * 
	 * @return departures - Xpath of departures radio button
	 */
	public By getDeparturesXpath(){
		return DeparturesRadioButton;
	}
	
	/**
	 * Method providing xpath of Interval selectbox in the form
	 * for finding flights
	 * 
	 * @return IntervalSelectbox - Xpath of interval selectbox
	 */
	public By getIntervalSelectboxXpath(){
		return IntervalSelectbox;
	}
	
	/**
	 * Method providing xpath of Interval +1 Month from chosen day in selectbox
	 * 
	 * @return Interval1Month - Xpath of Interval +1 Month from chosen day in selectbox
	 */
	public By getInterval1MonthXpath(){
		return Interval1Month;
	}
	
	/**
	 * Method providing xpath of search button in the form 
	 * for finding flights
	 * 
	 * @return SearchButton - Xpath of search button
	 */
	public By getSearchButtonXpath(){
		return SearchButton;
	}
	
	/**
	 * Method providing xpath of airline in search result
	 * 
	 * @return SearchButton - Xpath of airline in search result
	 */
	public By getAirlineXpath(){
		return Airline;
	}
	
	/**
	 * Method providing xpath of destination in search result
	 * 
	 * @return SearchButton - Xpath of destination in search result
	 */
	public By getDestinationXpath(){
		return Destination;
	}
	
	/**
	 * Method providing Hash Map with airlines
	 * key = airline code, value = airline name 
	 * 
	 * @param driver - WebDriver
	 * @return HastMap<String, String> airlines - Hash Map of airlines flying from Ostrava
	 * Key = Code of airline, Value = Name of airline
	 */
	public HashMap<String, String> getAirlines(WebDriver driver){
		List<WebElement> airlinesList = driver.findElements(By.xpath("//*[@id='obsah']/div[1]/div[5]/div/div[1]/div/table/tbody/tr"));
		
		for(int i=1; i<= airlinesList.size(); i++){
			WebElement airlineCode = driver.findElement(By.xpath("//*[@id='obsah']/div[1]/div[5]/div/div[1]/div/table/tbody/tr["+ i +"]/td[1]"));
			WebElement airlineName = driver.findElement(By.xpath("//*[@id='obsah']/div[1]/div[5]/div/div[1]/div/table/tbody/tr["+ i +"]/td[2]"));
			
			airlines.put(airlineCode.getText(), airlineName.getText());
		}
		
		return airlines;
	} 
}
