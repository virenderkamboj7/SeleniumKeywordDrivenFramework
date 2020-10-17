package KeywordEngine;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import testCases.BaseClass;
import utilities.XLUtils;

public class KeywordEngine {
	
			public final String SCENARIO_SHEET_PATH = "C:\\Users\\orange\\eclipse-workspace\\"
					+ "SeleniumKeywordDriverFaramework\\src\\test\\java\\KeywordScenarios\\Scenario1.xlsx";
			public BaseClass base;
			public Properties prop;
			public WebDriver driver;
			public WebElement element;
			
			
			
			public void StartExecution(String sheetName) {
				
				String LocatorName = null;
				String LocatorValue = null;
				int sheetSize = 0;
				int k = 0;
				
				
					sheetSize = XLUtils.getRowCount(SCENARIO_SHEET_PATH, sheetName);
				
				for (int i=0; i<sheetSize; i++) {
					
					try {
					String LocatorColValue = XLUtils.getCellData(SCENARIO_SHEET_PATH, sheetName, i+1, k+1).trim(); //id=username
						if (!LocatorColValue.equalsIgnoreCase("NA")) {
						 LocatorName =	LocatorColValue.split("=")[0].trim();  //id
						 LocatorValue = LocatorColValue.split("=")[1].trim();  //username
						}
					
					String action =	XLUtils.getCellData(SCENARIO_SHEET_PATH, sheetName, i+1, k+2).toString().trim(); //Action
					String value = XLUtils.getCellData(SCENARIO_SHEET_PATH, sheetName, i+1, k+3).toString().trim();  //Value
					
					
					switch (action) { 			//Browser Actions
						case "open browser":
							base = new BaseClass();
							prop = base.init_properties();
							if(value.isEmpty() || value.equals("NA")) {
								driver = base.init_driver(prop.getProperty("browserName"));
							}
							else {
								driver = base.init_driver(value);
							}
							break;
						
						case "enter url":
							if(value.isEmpty() || value.equals("NA")) {
								driver.get(prop.getProperty("baseURL")); ///////*****////////////
							}
							else {
								driver.get(value);
							}
							
						case "quit":
							driver.quit();
							
						default:
							break;
						}
					
					switch (LocatorName) {			//Locators logics and cases
					
						case "id":
							WebElement element =driver.findElement(By.id(LocatorValue));
							if (action.equals("sendKeys")) {
								element.sendKeys(value);
							}
							else if (action.equals("click")) {
								element.click();
							}
							LocatorName = null;
							break;
						
						case "xpath":
							element = driver.findElement(By.xpath(LocatorValue));
							if (action.equals("sendKeys")) {
								element.sendKeys(value);
							}
							else if (action.equals("click")) {
								element.click();
							}
							LocatorName = null;
							break;
							
						default:
							break;
					}
					
				}
					catch (Exception e){
						System.out.println("Exception is: "+e);
						}
					}
				
			}
}
