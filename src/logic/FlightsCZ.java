package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.airports.BrnoAirport;
import pages.airports.OstravaAirport;
import pages.airports.PragueAirport;
import utility.ExcelUtils;

public class FlightsCZ {
	WebDriver driver;
	private static final String CHROME_DRIVER_WIN = "src//utility//chromedriver.exe";
	private static final String CHROME_DRIVER_LINUX = "//usr//local//bin//chromedriver";
	private PragueAirport destinationsPrague;
	private BrnoAirport destinationsBrno;
	private OstravaAirport destinationsOstrava;
	List<WebElement> possibleDestinations = new ArrayList<>();
	List<WebElement> possibleCountries = new ArrayList<>();
	List<WebElement> possibleAirlines = new ArrayList<>();
	ExcelUtils excel;
	
	/**
	 * Test that runs after testNG is initialized and 
	 * sets WebDriver for Windows and Linux. 
	 * @throws Exception 
	 */
	@BeforeTest
	public void setup() throws Exception{		
		String system = System.getProperty("os.name");
		if(system.toLowerCase().contains("windows")){
	    	System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_WIN);
	    } else {
	    	System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_LINUX);
	    }
		
		//one of those cities - Prague/Brno/Ostrava
		String city = "Prague";
		ExcelUtils.deleteData(city);
		
		driver = new ChromeDriver();
	  }
	  
	  /**
	   * Method that provides link with all 
	   * destinations of chosen airport
	   * 
	   * @param IATACode - IATA code of airport
	   * @return link -  webpage link where is possiblity
	   * to get all existing flights from airport 
	   */
	  public String getLink(String IATACode){
		  String link = "";
		  switch(IATACode){
		  		case "PRG":
		  			destinationsPrague = new PragueAirport(driver);
		  			link = destinationsPrague.getWebpage();
		  			break;
		  		case "BRQ":
		  			destinationsBrno = new BrnoAirport(driver);
		  			link = destinationsBrno.getWebpage();
		  			break;
		  		case "OSR":
		  			destinationsOstrava = new OstravaAirport(driver);
		  			link = destinationsOstrava.getWebpage();
		  			break;
		  }
		  return link;
	  }
	  
	  /**
	   * Test method which loads all possible destinations, countries and airlines
	   * from Prague airport and saves it in the excel file 
	   * 
	   * @throws Exception 
	   */
	  @Test(priority = 0)
	  public void pragueFlights() throws Exception{
			  destinationsPrague = new PragueAirport(driver);
  			  
			  driver.get(getLink("PRG"));
			  
			  possibleDestinations = destinationsPrague.getWebElements(destinationsPrague.getDestinationsXpath());
			  possibleCountries = destinationsPrague.getWebElements(destinationsPrague.getCountriesXpath());
			  possibleAirlines = destinationsPrague.getWebElements(destinationsPrague.getAirlinesXpath());
			  
			  saveFlight("Prague", possibleDestinations, possibleCountries, possibleAirlines);
	  }
	  
	  /**
	   * Test method which loads all possible destinations, countries and airlines
	   * from Brno airport and saves it in the excel file 
	   * 
	   * @throws Exception 
	   */
	  @Test(priority = 1)
	  public void brnoFlights() throws Exception{
		  driver.get(getLink("BRQ"));
		  destinationsBrno = new BrnoAirport(driver);
			  
		  WebElement arrivalCheckbox = driver.findElement(destinationsBrno.getArrivalsXpath());
		  arrivalCheckbox.click();
			  
		  WebElement interval = driver.findElement(destinationsBrno.getInterval1MonthXpath());
		  interval.click();
			  
		  WebElement searchButton = driver.findElement(destinationsBrno.getSearchButtonXpath());
  		  searchButton.click();
  		  
  		  List<WebElement> destinationsBRQ =  destinationsBrno.getWebElements(destinationsBrno.getDestinationsXpath());
  		  List<WebElement> airlinesBRQ = destinationsBrno.getWebElements(destinationsBrno.getAirlinesXpath());
  		  
  		  List<WebElement> flightsBRQ = sortFlights(destinationsBRQ, airlinesBRQ);
  		  
  		  for(int i=0; i < flightsBRQ.size(); i++){
  			  if( (i+1)%2 == 0){
  				  possibleAirlines.add(flightsBRQ.get(i));
  			  } else {
  				  possibleDestinations.add(flightsBRQ.get(i));  
  			  }
  		  }
  		  
  		saveFlight("Brno", possibleDestinations, possibleCountries, possibleAirlines);
	  }
	  
	  /**
	   * Test method which loads all possible destinations, countries and airlines
	   * from Ostrava airport and saves it in the excel file 
	   * 
	   * @throws Exception 
	   */
	  @Test(priority = 2)
	  public void ostravaFlights() throws Exception{
		  driver.get(getLink("OSR"));
		  destinationsOstrava = new OstravaAirport(driver);
		
		  WebElement departuresRadioButton = driver.findElement(destinationsOstrava.getDeparturesXpath());
		  departuresRadioButton.click();
		
		  WebElement intervalSelectBox = driver.findElement(destinationsOstrava.getIntervalSelectboxXpath());
		  intervalSelectBox.click();
			  		
		  WebDriverWait wait = new WebDriverWait(driver, 1);			  		
		  WebElement interval = wait.until((ExpectedConditions.elementToBeClickable(destinationsOstrava.getInterval1MonthXpath()))); 
		  interval.click();
		
		  WebElement searchButton = driver.findElement(destinationsOstrava.getSearchButtonXpath());
		  searchButton.click();
		
		  HashMap<String, String> ostravaAirlines = destinationsOstrava.getAirlines(driver);
		
		  List<WebElement> destinationsOSR =  destinationsOstrava.getWebElements(destinationsOstrava.getDestinationXpath());
		  List<WebElement> airlinesOSR = destinationsOstrava.getWebElements(destinationsOstrava.getAirlineXpath());
					  		
		  List<WebElement> flightsOSR = sortFlights(destinationsOSR, airlinesOSR);
		
		  for(int i = 0; i<flightsOSR.size();i++){
			  if((i+1)%2 == 0){
				  possibleAirlines.add(flightsOSR.get(i));
			  } else {
				  possibleDestinations.add(flightsOSR.get(i));
			  }
		  }		
		  saveFlight("Ostrava", possibleDestinations, possibleCountries, possibleAirlines, ostravaAirlines);
	  }
	  
	  /**
	   * Method for adding all destination and airlines in one list. Duplicates are dropped
	   * 
	   * Used in case when airport doesn't provide
	   * information about all possible flights on one page.
	   * 
	   * @param destinations - List containing all destinations from one airport
	   * @param airlines - List containing all airlines from one airport
	   * @return sortedFlights - List containing destinations (every odd) and airlines (every even)
	   * without duplicates
	   */
	 private List<WebElement> sortFlights(List<WebElement> destinations, List<WebElement> airlines){
		 List<WebElement> sortedFlights = new ArrayList<>();
		 List<String> flights = new ArrayList<>();
		 String flight;
		 
		 for(int i=0; i<destinations.size(); i++){
			 flight = destinations.get(i).getText() + ";" + airlines.get(i).getText();
			 
			 if(!flights.contains(flight)){
				 flights.add(flight);
				 sortedFlights.add(destinations.get(i));
				 sortedFlights.add(airlines.get(i));
			 }			 
		 }
		 return sortedFlights;
	 }
	  
	  /**
	   * Method for saving all possible flights from airport (except Ostrava)
	   * in the excel file
	   * 
	   * Excel file structure: departure airport|city of arrival|country of arrival|airline
	   * 
	   * @param originCity - City of departure
	   * @param destinations - List of arrival cities
	   * @param countries - List of Country of arrival
	   * @param airlines - List of airline providing flight
	   * 
	   * @throws Exception
	   */
	  public void saveFlight(String originCity, List<WebElement> destinations, List<WebElement> countries, List<WebElement> airlines) throws Exception{				  		  
		  String destination;
		  String country;
		  String airline;		  
		  
		  for(int i=0; i < destinations.size(); i++){
			  destination = destinations.get(i).getText();
			 
			  if(countries.size() != 0){
				  country = countries.get(i).getText();
			  } else {
				  country = "";
			  }			  
			  
			  airline = airlines.get(i).getText();
			  
			  String[] toWrite = {originCity, destination, country, airline};		  
			  ExcelUtils.writeData(toWrite);
  		  }
		  
		  destinations.clear();
		  countries.clear();
		  airlines.clear();
	  }
	  
	/**
	 * 	 
	 * Method used for saving possible flights from Ostrava airport
	 * in the excel file
	 * Excel file structure: departure airport|city of arrival|country of arrival|airline
	 * 
	 * @param originCity - City of departure
	 * @param destinations - List of arrival cities
	 * @param countries - List of Country of arrival
	 * @param airlines - List of airline providing flight
	 * @param ostravaAirlines - HashMap of airlines flying from Ostrava
	 * @throws Exception
	 */
	  public void saveFlight(String originCity, List<WebElement> destinations, List<WebElement> countries, List<WebElement> airlines, HashMap<String, String> ostravaAirlines) throws Exception{				  		  
		  String destination;
		  String country = "";
		  String airline;
		  			  
		  for(int i=0; i < destinations.size(); i++){			  
			  destination = destinations.get(i).getText();	
			  
			  String airlinesString[] = airlines.get(i).getText().replaceAll("\\s", "").split(",");			  
			  for(String airlineString : airlinesString){
 				  airline = (String) ostravaAirlines.get(airlineString.substring(0, 2));
				  String[] toWrite = {originCity, destination, country, airline};
				  excel.writeData(toWrite);
			  }
  		  }
		  
		  destinations.clear();
		  countries.clear();
		  airlines.clear();
	  }
	  
	  /**
	   * Method which closes driver when test ends
	   */
	  @AfterTest
	  public void teardown(){
		  driver.close();
	  }
	  
}
