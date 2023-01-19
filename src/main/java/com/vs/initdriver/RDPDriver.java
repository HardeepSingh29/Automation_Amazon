package com.vs.initdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.paulhammant.ngwebdriver.NgWebDriver;
import com.vs.config.Config;
import com.vs.executor.CukeExecutor;
import com.vs.libraries.Log;

/**
 * This Class will initiate the explorer drivers. Config class is extended by
 * this class.
 * 
 * @author ArunYadav
 *
 */
public class RDPDriver extends Config {

	public static RDPDriver oRDPDriver;
	public static NgWebDriver oNJSDriver;
	public static WebDriver driver;

	/**
	 * This Constructor will initiate different explorer based on the browser name
	 * passed into INI file.
	 * 
	 * @author ArunYadav
	 */
	private RDPDriver() {

		try {

			if (browserName.equalsIgnoreCase("CHROME")) {
				System.setProperty("webdriver.chrome.driver", projPath + driverRelativePath + "chromedriver.exe");
				RDPDriver.driver = new ChromeDriver();
				Log.logInfo(RDPDriver.class, "Created Chrome driver instace");

			} else if (browserName.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", projPath + driverRelativePath + "IEDriverServer.exe");
				RDPDriver.driver = new InternetExplorerDriver();
				Log.logInfo(RDPDriver.class, "Created IE driver instace");

			} else if (browserName.equalsIgnoreCase("FIREFOX")) {
				System.setProperty("webdriver.gecko.driver", projPath + driverRelativePath + "geckodriver.exe");
				RDPDriver.driver = new FirefoxDriver();
				Log.logInfo(RDPDriver.class, "Created FireFox driver instace");
			}

			RDPDriver.driver.manage().window().maximize();
			Log.logInfo(RDPDriver.class, "Maximize the explorer window");
			RDPDriver.driver.manage().timeouts().implicitlyWait(objectWaitTime, TimeUnit.SECONDS);

		} catch (Exception e) {
			Log.logError(RDPDriver.class, e.getMessage());
		}
	}

	/**
	 * This method will help to call the constructor of this class only.
	 * 
	 * @author ArunYadav
	 */
	public static void setUpRDPDriver() {

		try {

			if (RDPDriver.driver == null) {
				Log.logInfo(RDPDriver.class, "Setup the new driver");
				oRDPDriver = new RDPDriver();
			}
		} catch (Exception e) {
			Log.logError(RDPDriver.class, e.getMessage());
		}
	}

	/**
	 * This method will initiate NJ drivers, which is further used in Angular
	 * application automation.
	 * 
	 * @author ArunYadav
	 * @return - Returning the NgWebDriver Instance.
	 */
	public static NgWebDriver getNJSDriver() {
		try {
			oNJSDriver = new NgWebDriver((JavascriptExecutor) RDPDriver.getDriver());
			return oNJSDriver;
		} catch (Exception e) {
			Log.logError(RDPDriver.class, e.getMessage());
			return null;
		}

	}

	/**
	 * Getting the driver instance.
	 * 
	 * @author ArunYadav
	 * @return Returning the driver instance.
	 */
	public static WebDriver getDriver() {
		return RDPDriver.driver;
	}

}
