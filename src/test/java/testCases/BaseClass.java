package testCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.google.common.io.Files;

import utilities.ReadConfig;

public class BaseClass {
	public static WebDriver driver;
	public static Logger logger;
	public Properties prop;
	
	//Properties file
	ReadConfig readConfig=new ReadConfig();  //Object of properties file
	public String baseURL=readConfig.baseURL();  //Base URL  
	public String chromedriverpath=readConfig.chromedriver(); //Chromepath
	public String firefoxpath=readConfig.firefoxdriver(); //Firefox path
	public String mobile=readConfig.mobile(); // Mobile Number
	public String password=readConfig.password(); //Password
	public String email=readConfig.email();  //Email
	
	public static WebDriverWait wait;
	
	public static DateTimeFormatter dtf; //Date time formater
	public static LocalDateTime now; //Get local time
	
	
	public void setup(String br) {

				if(br.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",chromedriverpath);
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			wait=new WebDriverWait(driver, 20);	
		}
		
		else if(br.equals("firefox")) {
			System.setProperty("webdriver.chrome.driver",firefoxpath);
			driver=new FirefoxDriver();
			driver.manage().window().maximize(); 
			wait=new WebDriverWait(driver, 20);	
		}		
	}		
	

	//@AfterClass
	public void tearDown() {
		driver.quit();	
	}
	

	@BeforeClass
	public void tc(String brr) {
		//logger=Logger.getLogger("BaseClass");
		logger=Logger.getLogger(getClass());
		BasicConfigurator.configure();
		PropertyConfigurator.configure("log4j.properties");
		BaseClass br=new BaseClass();
		br.setup(brr);
		driver.get(baseURL);
		logger.info("URL Opned");	
	}
	
	
	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		Files.copy(source, target);
		System.out.println("Screenshot taken");
	}

	
	
	@AfterMethod
	public void screenShot(ITestResult result){
		
		if(ITestResult.FAILURE==result.getStatus()){
			try{
				dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm.ss");  
				   now = LocalDateTime.now();  
				   System.out.println("Current Time is: "+dtf.format(now));  
     				captureScreen(driver, driver.getTitle()+dtf.format(now));
			
				System.out.println("Successfully captured a screenshot named as: "+driver.getTitle()+" "+dtf.format(now));
			}catch (Exception e){
				System.out.println("Exception while taking screenshot "+e.getMessage());
			} 
	}
	driver.quit();
	}
	
	
	public Properties init_properties() {
		
		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream("./Configuration\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
		
	}
	
	public WebDriver init_driver(String br) {
		setup(br);
		return driver;		
		}	
	
}
