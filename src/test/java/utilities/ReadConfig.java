package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
	
	public static Properties pro;
	
	public ReadConfig() {
		File src=new File("./Configuration\\config.properties");
		
		try {
			FileInputStream fis= new FileInputStream(src);
			pro= new Properties();
			pro.load(fis);
		}
		catch(Exception e){
			System.out.println("Exception is: " +e.getMessage());
		}
	}
	
	public String baseURL() {
		String url = pro.getProperty("baseURL");
		return url;		
	}
	
	public String chromedriver() {
		String cdriver=pro.getProperty("chromepath");
		return cdriver;
	}
	
	public String firefoxdriver() {
		String fdriver=pro.getProperty("firefoxpath");
		return fdriver;
	}

	//////////////////////////User Information////////////////////////
	//Mobile Number
	public String mobile() {
		String mobile=pro.getProperty("mobile");
		return mobile;
	}
	
	//Password
	public String password() {
		String password=pro.getProperty("password");
		return password;
	}
	
	// Email
	public String email() {
		String email=pro.getProperty("email");
		return email;
	}
	
	// Name
		public String name() {
			String name=pro.getProperty("name");
			return name;
		}
	
	
		
	/////////////////////Card Detials////////////////////////////
	//Cardnumber
		public String cardnum() {
			String cardnum=pro.getProperty("cardnum");
			return cardnum;
		}
	
	//Expiry Date	
		public String exp() {
			String exp=pro.getProperty("exp");
			return exp;
		}
		
		
	//CVV	
		public String cvv() {
			String cvv=pro.getProperty("cvv");
			return cvv;
		}
		
		
	//Card Number	
		public String nameoncard() {
			String nameoncard=pro.getProperty("nameoncard");
			return nameoncard;
		}
		
}

