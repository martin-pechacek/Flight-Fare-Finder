package utility;

public class City {

	String cityName;
	
	/**
	 * Class constructor
	 * 
	 * @param cityName - Name of City
	 */
	public City(String cityName){
		this.cityName = cityName;
	}
	
	/** 
	 * @return Country where is city located
	 */
	public String getCountry(){
		switch(cityName.toLowerCase()){
			case "prague":
				return "Czech Republic";
			case "brno":
				return "Czech Republic";
			case "ostrava":
				return "Czech Republic";
			default:
				return "";
		}
	}
}
