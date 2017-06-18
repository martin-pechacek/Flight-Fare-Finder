package utility;

public class City {

	String cityName;
	
	public City(String cityName){
		this.cityName = cityName;
	}
	
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
