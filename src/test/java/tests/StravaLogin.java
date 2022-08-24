package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;

public class StravaLogin {
	
	ConfigReader properties = new ConfigReader();
	public static String authCode;
	String currentUrl;
	String[] urlData;
	String[] finalData;
	
	public String makeUserAuthorize() throws Throwable {
		
		// URL for authorizing the user
		String url = "https://www.strava.com/oauth/authorize?" + "client_id="
				+ properties.getProperty("client_id")
				+ "&response_type=code&redirect_uri=http://localhost/exchange_token&approval_prompt=force&scope=activity:read_all";

		// Login Page URL
		String uri = "https://www.strava.com/login";
		
		//Headless WebDriver Setup
		WebDriverManager.chromedriver().setup();
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("headless");
		WebDriver driver = new ChromeDriver(opt);
		System.out.println("WebDriver Setup Complete");
		
		// Login to strava 
		driver.get(uri);
		System.out.println("Successfully visited www.strava.com");
		driver.findElement(By.id("email")).sendKeys(properties.getProperty("email"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("password"));
		driver.findElement(By.id("login-button")).click();
		System.out.println("Successfully logged in to Strava");
		
		// Authorizing the user
		driver.get(url);
		System.out.println("URL hitted");
		driver.findElement(By.id("authorize")).click();
		System.out.println("User Successfully authorized");

		// Splitting the url to retrieve the authorization code
		currentUrl = driver.getCurrentUrl();
		urlData = currentUrl.split("&");
		finalData = urlData[1].split("=");
		authCode = finalData[1];

		return authCode;

	}

}
