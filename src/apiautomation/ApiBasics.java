package apiautomation;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import files.Payload;

public class ApiBasics 
{
	public static void main(String[] args) 
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//========================= Add Place ==================================
		String response = given().log().all().queryParam("key", "qaclick123").header("content-type","application/json")
		.body(Payload.addPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("server","Apache/2.4.18 (Ubuntu)").extract().response().asString();
		System.out.println("============ Response of request is =========:"+"\n"+response);
		
		JsonPath js = new JsonPath(response); //for parsing Json
		String placeId = js.getString("place_id");	
		System.out.println("Place id is:  "+placeId);
		
		//======================= Update Place ==================================
		String newAddress = "70 Harsha walk, USA";
		given().log().all().queryParam("key","qaclick123").header("content-type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//====================== Get Place ===================================
		String  getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
								  .queryParam("place_id",placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = new JsonPath(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);	
		Assert.assertEquals(actualAddress, newAddress);
		
	}
}
