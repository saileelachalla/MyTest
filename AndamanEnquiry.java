package dekhoAndaman;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AndamanEnquiry {

	
	WebDriver driver;
	 
@BeforeTest
	 public void launch() 
	 {
		    System.setProperty("webdriver.chrome.driver","/home/revathi/Desktop/chromedriver");
		 
	  		driver = new ChromeDriver();
	  		
	  		driver.get("http://192.168.2.40/dekho-andaman/public/");
	  		
	  		driver.manage().window().maximize();
	  		
	  		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS );
	  		
	  		driver.findElement(By.xpath("//*[@id='navbarTogglerDemo03']/ul/li[8]/a")).click();//to locate the contact element

	  		JavascriptExecutor js = (JavascriptExecutor) driver;  //<!!!!!!-----for scrolling the screen to -----!!!!!!!
	  		
	  		js.executeScript("window.scrollBy(0,1000)");
		 
	 }

	@Test(dataProvider = "getData")
	  public void demo(HashMap<String, String> d) throws InterruptedException
  {
		
		System.out.println(" sdadadd -->"+d.get("destinationcity"));
		driver.findElement(By.id("departure-city")).sendKeys(d.get("destinationcity"));//departure city
  		Thread.sleep(2000);
  		
  		Select traveltype = new Select(driver.findElement(By.id("departure-travel")));//types of travels

         List<WebElement> dd = traveltype.getOptions();//Get all options
         
         System.out.println(dd.size());//Get the length
         for (int j = 0; j < dd.size(); j++)// Loop to print one by one
         {	  	 
        	 String str1=dd.get(j).getText();
        	 if(str1.equalsIgnoreCase(d.get("selecttraveltype"))) {
        		 dd.get(j).click();
        		 break;
        	 }
         System.out.println(str1);  
         }
        
         driver.findElement(By.id("departure-message")).sendKeys(d.get("specialrequirements"));//special message requirements
  		  Thread.sleep(2000);

  		  Select numbofdays = new Select(driver.findElement(By.id("departure-tourdays")));//selected number of days
  		  
          List<WebElement> dd1 = numbofdays.getOptions();//Get all options
          
          System.out.println(dd1.size());//Get the length
           for (int j = 0; j < dd1.size(); j++)// Loop to print one by one
           {
           String str2=dd1.get(j).getText();
        	 if(str2.equalsIgnoreCase(d.get("numberofdays")))
        	 {
        		 dd1.get(j).click();
        		 break;	 	   
           System.out.println(dd1.get(j).getText());
           }
  		
	      driver.findElement(By.id("departure-persons")).sendKeys(d.get("numberofpersons"));
 		  Thread.sleep(2000);

  		driver.findElement(By.id("departure-customername")).sendKeys(d.get("fullname"));
  		Thread.sleep(2000);
  		
  		driver.findElement(By.id("departure-customernumber")).sendKeys(d.get("phonenumber"));
  		Thread.sleep(2000);
  		
  		driver.findElement(By.id("departure-customeremail")).sendKeys(d.get("emailaddress"));
  		Thread.sleep(2000);
  		
  	    driver.findElement(By.id("departure-submitbtn")).click();
  		Thread.sleep(2000);

  		
  		try
		{
  		driver.findElement(By.id("departure-city")).clear();
		//driver.findElement(By.id("departure-travel")).clear();
		driver.findElement(By.id("departure-message")).clear();
		//driver.findElement(By.id("departure-tourdays")).clear();
		driver.findElement(By.id("departure-persons")).clear();
		driver.findElement(By.id("departure-customername")).clear();
		driver.findElement(By.id("departure-customernumber")).clear();
		driver.findElement(By.id("departure-customeremail")).clear();
		
	}catch(Exception e) {}
  		}
	
  }
	
	 @DataProvider
		public static Object[][] getData() throws IOException {

			File f = new File("/home/revathi/DDT.xlsx");
			FileInputStream fis = new FileInputStream(f);
			Workbook w = null;
			try {
				w = WorkbookFactory.create(fis);
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			Sheet s = w.getSheet("contact");
			int rowCount = s.getLastRowNum();
			int colCount = s.getRow(0).getLastCellNum();
			System.out.println("rows count" + rowCount);
			System.out.println("coloumns count" + colCount);
			Object[][] obj = new Object[rowCount][1];

			for (int i = 1; i <= rowCount; i++) {
				Map<String, String> data = new HashMap<>();
				for (int j = 0; j < colCount; j++) {


					DataFormatter df = new DataFormatter();
					String key = df.formatCellValue(s.getRow(0).getCell(j));
					String value = df.formatCellValue(s.getRow(i).getCell(j));
					data.put(key, value);

				}
				obj[i - 1][0] = data;
			}
			return obj;

	 }	
	
	
}
	
	
	
	
	
	
	

