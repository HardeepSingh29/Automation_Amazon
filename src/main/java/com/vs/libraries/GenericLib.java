package com.vs.libraries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cucumber.listener.Reporter;
import com.google.common.base.Throwables;
import com.google.common.io.Files;
import com.vs.Stepdefinition.UniversalSteps;
import com.vs.config.Config;
import com.vs.config.PropertiesCache;
import com.vs.initdriver.RDPDriver;

/**
 * This class will have Common methods which will be used through out the code.
 **/
public class GenericLib {
	public static String db1;
	public static String db2;
	public static String vDbInstanceName;
	public String currentDate;
	public static Xls_Reader oDBLogXL = null;
	static WebDriverWait webDriverwait = new WebDriverWait(RDPDriver.getDriver(), Config.objectWaitTime);


	public static void openURL(String urlName) throws Exception {
		try {
			RDPDriver.getDriver().get(urlName);
			RDPDriver.getDriver().manage().timeouts().pageLoadTimeout(Config.pageLoadTime, TimeUnit.SECONDS);
			Log.logInfo(GenericLib.class, "URL launched");
			GlobalVariables.testStatus = true;
		} catch (Exception e) {
			GlobalVariables.testStatus = false;
			Log.logError(GenericLib.class, "Error while launching " + e.getMessage());
		}
	}

	/**
	 * Name: getCurrentURL Description : This function will get the URL of the
	 * current User.
	 * 
	 * @author Gundeep Singh Date: 11/06/2019
	 **/
	public static String getCurrentURL() throws Exception {
		Log.logInfo(GenericLib.class, "Current URL is fetched");
		GlobalVariables.testStatus = true;
		return RDPDriver.getDriver().getCurrentUrl().trim();
	}

	/**
	 * Name: captureScreenShot Description: This method will take one parameter and
	 * saves the screenshot of the page.
	 * 
	 * @author e3003350
	 * @param pageName
	 **/
	

	public static String captureScreenShot(String pageName) throws IOException {
		// String screenshotName = scenario.getName().replaceAll(" ", "_");
		TakesScreenshot screen = (TakesScreenshot) RDPDriver.getDriver();
		File sourcePath = screen.getScreenshotAs(OutputType.FILE);
		System.out.println("This is the source path ************   " + sourcePath);
		String destinationActualPath = System.getProperty("user.dir") + "/DigiPayZelleAutomation/target/" + pageName
				+ ".png";
		File destinationPath = new File(destinationActualPath);
		System.out.println("This is the destination path ***********  " + destinationPath);
		// Copy taken screenshot from source location to destination location
		Files.copy(sourcePath, destinationPath);
		return destinationActualPath;
		// This attach the specified screenshot to the test

	}

	/**
	 * Name: refreshBrowser Description :This method will refresh the browser.
	 * 
	 * @author Ram Singh Yadav
	 * @param refreshCount
	 **/
	public static void refreshBrowser(int refreshCount) {
		int refreshTime;
		for (refreshTime = 1; refreshTime <= refreshCount; refreshTime++) {
			RDPDriver.getDriver().navigate().refresh();
		}
		Log.logInfo(GenericLib.class, "User refreshed current page " + refreshCount + " times");
	}

	public static void reloadUrlBrowser(String urlName) throws Exception {
		// int refreshTime;
		// for (refreshTime = 1; refreshTime <= refreshCount; refreshTime++) {
		try {
			RDPDriver.getDriver().navigate().to(urlName);
			GlobalVariables.testStatus = true;
			// }
			Log.logInfo(GenericLib.class, "Reloaded the given url in same browser " + urlName + " ...");
		} catch (Exception e) {
			GlobalVariables.testStatus = false;
			Log.logError(GenericLib.class, "Error while launching " + e.getMessage());
		}
	}

	/**
	 * Name: switchWindows Description: This function will switch to the other
	 * window
	 * 
	 * @author Gundeep Singh Date : 02/04/2020
	 **/
	public static void switchWindows() {
		String currentWindow = RDPDriver.getDriver().getWindowHandle();
		System.out.println("Current window is " + currentWindow);
		Set<String> allWindows = RDPDriver.getDriver().getWindowHandles();
		for (String previousWindow : allWindows) {
			if (!currentWindow.equalsIgnoreCase(previousWindow)) {
				RDPDriver.getDriver().switchTo().window(previousWindow);
				System.out.println(RDPDriver.getDriver().switchTo().window(previousWindow).getTitle());
				Log.logInfo(GenericLib.class, "Switched to new Window");
			}
		}
	}

	/**
	 * Name :closeBrowser Description : This method is getting close Browser
	 * 
	 * @author Sumit Dhiman Date : 02/04/2020
	 **/
	public static void closeBrowser() {
		try {
			RDPDriver.getDriver().close();
			Log.logInfo(GenericLib.class, "Browser is closed");
		} catch (Exception e) {
			Log.logError(GenericLib.class, "Error is dispalyed while closing the browser: " + e.getMessage());
		}
	}

	/**
	 * Name :closeBrowser Description : This method will generate current date in
	 * MM/dd/yyyy format.
	 * 
	 * @author Sahil Gupta Date : 04/28/2020
	 **/
	public static void getDate() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDateTime now = LocalDateTime.now();
			PropertiesCache.setCacheProperty("currentDate", dtf.format(now));
			Log.logInfo(GenericLib.class, "Current date created");
			GlobalVariables.testStatus = true;
		} catch (Exception e) {
			Log.logError(GenericLib.class, "Exception while creating current date: " + e.getMessage());
			GlobalVariables.testStatus = false;
		}

	}
	

     }
 
     
       


