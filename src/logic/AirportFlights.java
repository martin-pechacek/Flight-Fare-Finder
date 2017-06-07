package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.airports.BrnoDestinations;
import pages.airports.OstravaDestinations;
import pages.airports.PragueDestinations;
import utility.ExcelUtils;

public class AirportFlights {
	WebDriver driver;
	private static final String CHROME_DRIVER = System.getProperty("user.dir")+"//src//utility//chromedriver.exe";
	private PragueDestinations destinationsPrague;
	private BrnoDestinations destinationsBrno;
	private OstravaDestinations destinationsOstrava;
	
	
	  @BeforeTest
	  public void setup(){
			System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
			driver = new ChromeDriver();
	  }
	  
	  /**
	   * Method that provides link with all destinations of chosen airport
	   * 
	   * @param IATACode
	   * @return link
	   */
	  public String getLink(String IATACode){
		  String link = "";
		  switch(IATACode){
		  		case "PRG":
		  			destinationsPrague = new PragueDestinations(driver);
		  			link = destinationsPrague.getWebpage();
		  			break;
		  		case "BRQ":
		  			destinationsBrno = new BrnoDestinations(driver);
		  			link = destinationsBrno.getWebpage();
		  			break;
		  		case "OSR":
		  			destinationsOstrava = new OstravaDestinations(driver);
		  			link = destinationsOstrava.getWebpage();
		  			break;
		  }
		  return link;
	  }
	  
	  /**
	   * Method which loads all possible destinations, countries and airline 
	   * and store it in the List<WebElement>
	   * 
	   * @param countryCode
	   * @throws Exception 
	   */
	  @Test
	  public void getDestinations() throws Exception{	 
		  List<WebElement> possibleDestinations = new ArrayList<>();
		  List<WebElement> possibleCountries = new ArrayList<>();
		  List<WebElement> possibleAirlines = new ArrayList<>();
		  
		  String countryCode = "cz";
		  
		  switch(countryCode){
		  			case "cz":		
		  			  //destinations from Prague
		  			  destinationsPrague = new PragueDestinations(driver);
		  			  
		  			  driver.get(getLink("PRG"));
		  			  
		  			  possibleDestinations = destinationsPrague.getWebElements(destinationsPrague.getDestinationsXpath());
		  			  possibleCountries = destinationsPrague.getWebElements(destinationsPrague.getCountriesXpath());
		  			  possibleAirlines = destinationsPrague.getWebElements(destinationsPrague.getAirlinesXpath());
		  			  
		  			  saveFlight("Prague", possibleDestinations, possibleCountries, possibleAirlines);
		  			 
		  			 
		  			  //destinations from Brno
		  			  driver.get(getLink("BRQ"));
		  			  destinationsBrno = new BrnoDestinations(driver);
		  			  
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

			  		//destinations from Ostrava
			  		driver.get(getLink("OSR"));
			  		destinationsOstrava = new OstravaDestinations(driver);
			  		
			  		WebElement departuresRadioButton = driver.findElement(destinationsOstrava.getDeparturesXpath());
			  		departuresRadioButton.click();
			  		
			  		WebElement intervalSelectBox = driver.findElement(destinationsOstrava.getIntervalSelectboxXpath());
			  		intervalSelectBox.click();
			  			  		
			  		WebDriverWait wait = new WebDriverWait(driver, 1);			  		
			  		interval = wait.until((ExpectedConditions.elementToBeClickable(destinationsOstrava.getInterval1MonthXpath()))); 
			  		interval.click();
			  		
			  		searchButton = driver.findElement(destinationsOstrava.getSearchButtonXpath());
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
	  }
	  
	  /**
	   * Method for sorting same flights in the list.
	   * Used in case when airport doesn't provide
	   * information about all possible flights. And has to be
	   * 
	   * Sorted flights are in list saved in 
	   * structure - every odd - destination, every even - airline
	   * used ordinary flight search. e.g. Brno 
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
	   * Method used for saving possible flights from airport (except Ostrava)
	   * in the excel file
	   * 
	   * Excel file structure: departure airport|city of arrival|country of arrival|airline
	   *  
	   * @throws Exception
	   */
	  public void saveFlight(String airport, List<WebElement> destinations, List<WebElement> countries, List<WebElement> airlines) throws Exception{				  		  
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
			  
			  String[] toWrite = {airport, destination, country, airline};		  
			  ExcelUtils.writeData(toWrite, "Czech Republic");
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
	 * @param airport
	 * @param destinations
	 * @param countries
	 * @param airlines
	 * @param ostravaAirlines
	 * @throws Exception
	 */
	  public void saveFlight(String airport, List<WebElement> destinations, List<WebElement> countries, List<WebElement> airlines, HashMap<String, String> ostravaAirlines) throws Exception{				  		  
		  String destination;
		  String country = "";
		  String airline;
		  			  
		  for(int i=0; i < destinations.size(); i++){			  
			  destination = destinations.get(i).getText();	
			  
			  String airlinesString[] = airlines.get(i).getText().split(",");			  
			  for(String airlineString : airlinesString){
				  airline = (String) ostravaAirlines.get(airlineString.substring(0, 2));		  
				  String[] toWrite = {airport, destination, country, airline};
				  ExcelUtils.writeData(toWrite, "Czech Republic");
			  }
  		  }
		  
		  destinations.clear();
		  countries.clear();
		  airlines.clear();
	  }
	  
	  @AfterTest
		public void teardown(){
			driver.close();
		}
	  
}
