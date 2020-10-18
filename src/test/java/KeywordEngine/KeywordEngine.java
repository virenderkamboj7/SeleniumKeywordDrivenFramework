package KeywordEngine;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import testCases.BaseClass;
import utilities.XLUtils;

public class KeywordEngine {
//			/Drivers\\chromedriver.exe
	
			public final String SCENARIO_SHEET_PATH = "./src\\test\\java\\KeywordScenarios\\Scenario1.xlsx";
			public BaseClass base;
			public Properties prop;
			public WebDriver driver;
			public WebElement element;
			public Actions act;
			
			
			public void StartExecution(String sheetName) {
				
				String LocatorName = null;
				String LocatorValue = null;
				int sheetSize = 0;
				int k = 0;
//				Select sel = new Select();
				
				
					sheetSize = XLUtils.getRowCount(SCENARIO_SHEET_PATH, sheetName);
				
				for (int i=0; i<sheetSize; i++) {
					
					
					String LocatorColValue = XLUtils.getCellData(SCENARIO_SHEET_PATH, sheetName, i+1, k+1).trim(); //id=username
						if (!LocatorColValue.equalsIgnoreCase("NA")) {
						 LocatorName =	LocatorColValue.split("%")[0].trim();  //id
						 LocatorValue = LocatorColValue.split("%")[1].trim();  //username
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
								driver.get(prop.getProperty("baseURL"));
							}
							else {
								driver.get(value);
							}
							
//						case "quit":
//							driver.quit();
							
						default:
							break;
						}
					
					
				try {
					switch (LocatorName) {			//Locators logics and cases
					
						//Clear input field
						case "clear":
							element =driver.findElement(By.id(LocatorValue));
							element.clear();

							
						case "id":
							element =driver.findElement(By.id(LocatorValue));
							if (action.equals("sendKeys")) {
								element.clear();
								element.sendKeys(value);
							}
							else if (action.equals("click")) {
								element.click();
							}
							else if(action.equals("hover")) {
								element =driver.findElement(By.id(LocatorValue));
								act = new Actions(driver);
								act.moveToElement(element).build().perform();
							}
							else if(action.equals("hoverClick")) {
								element =driver.findElement(By.id(LocatorValue));
								act.moveToElement(element).click().build().perform();
							}
							LocatorName = null;
							break;
						
						case "xpath":
							element = driver.findElement(By.xpath(LocatorValue));
							if (action.equals("sendKeys")) {
								element.clear();
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
					catch (NullPointerException e){
						//System.out.println("NullPointerException is: "+e);
						}
					
					switch (action) {
					case "quit":
						driver.quit();
					
					}
					
				}
					
			}
				
		}

