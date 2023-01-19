package com.vs.executor;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.PropertyConfigurator;
import org.junit.runner.RunWith;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.cucumber.listener.ExtentProperties;
import com.vs.initdriver.RDPDriver;
import com.vs.libraries.Log;

import cucumber.api.junit.Cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;

/**
 * Cucumber option annotations for binding the feature files, step definition
 * files. Also defining the extent cucumber report for report generation.
 * 
 * @author GaytriSarin
 *
 */
@CucumberOptions(

		features = {
				"C:\\Users\\HARDEEP SINGH\\eclipse-workspace\\Automation_VS_New\\src\\main\\resources\\features\\LandingPage.Feature" }, glue = {
						"com.vs.Stepdefinition" },
		// plugin = {
		// "com.cucumber.listener.ExtentCucumberFormatter:" },
		monochrome = true
// plugin = { "pretty", "html:/DigiPayZelleAutomation/target" }
// tags = {"@CommonTC1"}
)

@RunWith(Cucumber.class)

public class CukeExecutor extends AbstractTestNGCucumberTests {
	static String userDir = System.getProperty("user.dir");
	// public static Xls_Reader logXl = null;

	/**
	 * This method will run before each test cases.
	 * 
	 * @author HardeepSingh
	 * 
	 */

	@BeforeTest
	public void initDriver() {

		try {

			PropertyConfigurator.configure(
					"C:\\Users\\HARDEEP SINGH\\eclipse-workspace\\Automation_VS_New\\src\\main\\resources\\properties\\Logger.properties");
			Log.logInfo(CukeExecutor.class, "Initiating drivers");
			RDPDriver.setUpRDPDriver();
			Log.logInfo(CukeExecutor.class, "Initialisation of drivers is completed successfully");
			// logXl = new
			// Xls_Reader("C:\\SeleniumWorkspace\\DigiPayZelleAutomation\\target\\log\\TestLog.xlsx");

			/*
			 * if (steps == null) { steps = new UniversalSteps(); }
			 */

		} catch (Exception e) {
			Log.logError(CukeExecutor.class, e.getMessage());
		}

	}

	/**
	 * This method will call initially before the start of test case. This will help
	 * user to create the extent reports.
	 * 
	 * @author HardeepSingh
	 */

	/*@BeforeClass
	public static void setup() {

		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM_dd_yyyy HH_mm_ss");
			LocalDateTime now = LocalDateTime.now();

			String currenTimeFormat = dtf.format(now);
			String cucumberFileName = "ZelleTestReport_" + currenTimeFormat;
			String loggerFileName = "ZelleLogger_" + currenTimeFormat;
			String loggerPropertyAbsolutePath = userDir + "\\src\\main\\java\\com\\epp\\config\\Logger.properties";

			PropertiesConfiguration propertiesConfig = new PropertiesConfiguration(loggerPropertyAbsolutePath);
			propertiesConfig.setProperty("log4j.appender.fileAppender.File",
					(userDir + "\\target\\TestLogs\\" + loggerFileName + ".log").replace("\\", File.separator));
			propertiesConfig.save();

			PropertyConfigurator.configure(loggerPropertyAbsolutePath);

			ExtentProperties extentProperties = ExtentProperties.INSTANCE;
			extentProperties.setReportPath(userDir + "/target/CucumberTestReports/" + cucumberFileName + ".html");

			Log.logInfo(CukeExecutor.class, "Initiating Loggers Instance with file name: " + loggerFileName);
			Log.logInfo(CukeExecutor.class, "Initiating Extent Reports Instance with file name: " + cucumberFileName);

			// deleteFilesOlderThanNdays(30,userDir+"/target/");

		}

		catch (Exception e) {
			Log.logError(CukeExecutor.class, e.getMessage());
		}
	}*/

}
