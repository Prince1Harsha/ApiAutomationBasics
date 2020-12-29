package apiautomation;
import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPrice 
{
	public static void main(String args[])
	{
		JsonPath js = new JsonPath(Payload.CoursePrice());
		
//		Test cases:
//		1. Print No of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
//		2.Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
//		3. Print Title of the first course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);
		
//		4. Print All course titles and their respective Prices
		for(int i=0;i<count;i++)
		{
			String courseTitles = js.get("courses["+i+"].title");
			System.out.println(js.get("courses["+i+"].price").toString());
			System.out.println(courseTitles);
		}
		
//		5. Print number of copies sold by RPA Course
		System.out.println("Number of copies sold by RPA course is:");
		for(int i=0;i<count;i++)
		{
			String courseTitles = js.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA"))
			{
				int copies = js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
	}
}
