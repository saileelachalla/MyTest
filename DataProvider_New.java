package testNGtutorials;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProvider_New {

	@Test(dataProvider = "getData")
	public void demo(HashMap<String, String> d) throws InterruptedException {
			
	WebDriver driver = null;
	
	System.setProperty("webdriver.chrome.driver","/home/revathi/Desktop/chromedriver");

	driver = new ChromeDriver();

	driver.get("http://192.168.2.62/topmarks_zf3_web/public/login");

	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
	System.out.println(d.get("Name"));
		/*System.out.println(d.get("email"));
		System.out.println(d.get("password"));
		System.out.println("Data has been taking from excel sheet");
		driver.findElement(By.id("dXNlcl9uYW1l")).sendKeys(d.get("email"));
		Thread.sleep(2000);
		driver.findElement(By.id("cGFzc3dvcmRfdXNlcg")).sendKeys(d.get("password"));
		Thread.sleep(2000);
        driver.findElement(By.id("bG9naW5fYnV0dG9u")).click();
		Thread.sleep(2000);*/

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
		Sheet s = w.getSheet("Test");
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
