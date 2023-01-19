package com.vs.config;

import java.io.File;

import org.ini4j.Ini;


/**
 * All the project level other variables will be initiate through this class.
 * @author GaytriSarin
 *
 */

public class Config {

	public static String browserName;
	public static String projectName;
	public static int objectWaitTime;
	public static int pageLoadTime;
	public static String url;
	public static String driverPath;
	public static String fusionRelativePath;
	public static String driverRelativePath;
	public static String dBInstanceAbsolutePath;
	public static String globalScenarioName = "";
	public static String cDbLogWSName = "DatabaseLogs";
	public static String cDbLogFirstCol = "TestCaseName";
	public static String cDbLogSecondCol = "Data";
	public static String cDbLogThirdCol = "Date";
	
	public static String projPath = System.getProperty("user.dir");
	public static String iniPath = projPath + "\\src\\main\\resources\\properties\\properties.ini";
	public static String dbLogPath = projPath
			+ "\\src\\main\\resources\\com\\epp\\testdata\\fusion\\DatabaseLogs.xlsx";
						
   
	public static String peoplePayUrl;    

	/**
	 * This Constructor will initiate all the variables from INI (properties file) file.
	 */
	
	public Config() {
		try {

			Ini ini = new Ini(new File(iniPath));
			browserName = ini.get("browser", "browsername");
			projectName = ini.get("browser", "projectname");
			objectWaitTime = Integer.parseInt(ini.get("browser", "objectwaittime"));
			pageLoadTime = Integer.parseInt(ini.get("browser", "pageloadtime"));
			url = ini.get("browser", "url");
			driverPath = ini.get("browser", "driverpath");
			driverRelativePath = ini.get("browser", "driverrelativepath");
			fusionRelativePath = ini.get("testdata", "fusionrelativepath");
			dBInstanceAbsolutePath = ini.get("common", "dbinstance");
			
			peoplePayUrl = ini.get("browser", "peoplepayurl");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
