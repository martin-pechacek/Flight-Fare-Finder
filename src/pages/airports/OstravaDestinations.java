package pages.airports;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OstravaDestinations extends Destinations {
	
	private String link = "http://www.airport-ostrava.cz/en/page-timetable/";
	By DeparturesRadioButton = By.xpath("//form[@class='jqtransform jqtransformdone']/table/tbody/tr[1]/td[2]/div[1]//a");
	By Interval1Month = By.xpath("//*[@id='obsah']/div[1]/div[3]/div[2]/form/table/tbody/tr[2]/td[4]/div/ul/li[3]/a");
	By SearchButton = By.xpath("//button[@name'send']");
	
	public OstravaDestinations(WebDriver driver){
		super(driver);
	}
	
	/**
	 * Method that provides link of airport web page where are all possible destinations 
	 * 
	 * @return link
	 */
	public String getWebpage(){
		return link;
	}
	
	/**
	 * Method providing xpath of departures radio button
	 * 
	 * @return departures
	 */
	public By getDeparturesXpath(){
		return DeparturesRadioButton;
	}
	
	/**
	 * Method providing xpath of Interval +1 Month from chosen day in selectbox
	 * 
	 * @return Interval1Month
	 */
	public By getInterval1MonthXpath(){
		return Interval1Month;
	}
	
	/**
	 * Method providing xpath of search button
	 * 
	 * @return SearchButton
	 */
	public By getSearchButtonXpath(){
		return SearchButton;
	}
}
