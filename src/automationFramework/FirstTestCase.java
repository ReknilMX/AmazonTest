package automationFramework;

import java.util.ArrayList;
import java.util.Collections;
import static org.junit.Assert.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FirstTestCase {
	
	public static void main(String[] args) {
		//Set geckodriver configurations
		System.setProperty("webdriver.gecko.driver", "/AmazonTest/driver/geckodriver.exe");
		
		// Create a new instance of the Firefox driver
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
        //1. Navigate to amazon.com
		driver.get("https://www.amazon.com/");
		
		//2. Search for "ipad air 2 case"
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("ipad air 2 case");
		driver.findElement(By.className("nav-input")).click();
		
		//3. Refine search (left hand side of site) to only show Plastic cases (Case Material)
		wait.until(ExpectedConditions.elementToBeClickable(By.name("s-ref-checkbox-8080061011")));
		driver.findElement(By.name("s-ref-checkbox-8080061011")).click();
		
		//4. Refine search  (left hand side of site) to only show results between the price $20 - $100
		wait.until(ExpectedConditions.elementToBeClickable(By.className("a-button-input")));
		driver.findElement(By.id("low-price")).sendKeys("20");
		driver.findElement(By.id("high-price")).sendKeys("100");
		driver.findElement(By.cssSelector("#a-autoid-4 > span > input")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"navFooter\"]/div[4]/ul/li[1]")));
		
		//5. Output the Name, Price and Score/Rating (Stars) of the first 5 results
		ArrayList<Items>  list = new ArrayList<Items>();
		for(int i=0; i<5; i++){
			String nameselector = String.format("#result_%s > div > div:nth-child(3) > div:nth-child(1) > a > h2", i);
			String priceselector = String.format("#result_%s > div > div:nth-child(4) > div:nth-child(1) > a > span", i);
			String scoreselector = String.format("//*[@id=\"result_%s\"]/div/div[6]/span/span/a/i[1]/span", i);
			
			String name = driver.findElement(By.cssSelector(nameselector)).getText();
			String pricetag = new String("0");
			pricetag =	driver.findElement(By.cssSelector(priceselector)).getAttribute("aria-label");
				pricetag = pricetag.replaceAll("\\$","");
				Float price = Float.valueOf(pricetag);
			String scoretag = driver.findElement(By.xpath(scoreselector)).getAttribute("innerHTML");
				scoretag = scoretag.replaceAll("\\ out of 5 stars","");
				Float score = Float.valueOf(scoretag);
    		list.add(new Items(i, name, price, score));
       }
		for(Items str: list){
			System.out.println(str);
	   }
		
		//6. Assert that the first 5 results are between $20 - $100
		for(int i=0; i<5; i++){	
			String priceselector = String.format("//*[@id=\"result_%s\"]/div/div[4]/div[1]/a/span[@class=\"a-color-base sx-zero-spacing\"]", i);
    		String pricetag = driver.findElement(By.xpath(priceselector)).getAttribute("aria-label");
    		pricetag = pricetag.replaceAll("\\$","");
    		Float price = Float.valueOf(pricetag);
    		
    		Assert.assertTrue("Error, price is too high", 100 >= price);
    		Assert.assertTrue("Error, price is too low", 20 <= price);
    		System.out.println("Price is between 20 - 100 : " + price);
       }
		
		//6. Sort the first 5 results by price (Using Java)
		   System.out.println("Item Price Sorting:");
		   Collections.sort(list, Items.ItemPriceComparator);

		   for(Items str: list){
				System.out.println(str);
		   }
		
		//7. Sort the first 5 results by Score/Rating (Using Java)
		   System.out.println("Item Score Sorting:");
		   Collections.sort(list, Items.ItemScoreComparator);

		   for(Items str: list){
				System.out.println(str);
		   }
		   
		 //7. Sort the first 5 results by price (Using Java) and Assert using testng / Junit that you have sorted the items correctly.   
		   System.out.println("Item Price Sorting Start");
		   Collections.sort(list, Items.ItemPriceComparator);
		   for(int i=0; i<4; i++){
			   assertTrue(list.get(i).getItemprice() < list.get(i+1).getItemprice());
		   }
		   System.out.println("Correct price sorting");
		   
		 //8. Based on Score and Cost recommend the item a user should purchase
		   System.out.println("Item Selection Start");
		   Collections.sort(list, Items.ItemScoreComparator);
		     
		   Float price1 = list.get(1).getItemprice();
		   Float price2 = list.get(2).getItemprice();
		   Float price3 = list.get(3).getItemprice();
		   
		   Float smallest = Math.min(price1, Math.min(price2, price3));
		   if(smallest.toString().equals(price1.toString()))
			   System.out.println("Recommended Item: " + list.get(1));
		   else if(smallest.toString().equals(price2.toString()))
			   System.out.println("Recommended Item: " + list.get(2));
		   else if(smallest.toString().equals(price3.toString()))
			   System.out.println("Recommended Item: " + list.get(3));
		   
        // Close the driver
        driver.quit();
    }
}