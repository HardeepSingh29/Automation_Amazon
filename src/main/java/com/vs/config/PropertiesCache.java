package com.vs.config;

import java.io.File;
import java.util.Iterator;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.vs.libraries.Log;

public class PropertiesCache {
	private static String propertiesCacheFileName = "PropertyCache.properties";
	private static String rtVariablesPath = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\properties\\";
	private static PropertiesConfiguration propertiesConfig;

	private PropertiesCache() {
		try {
			propertiesConfig = new PropertiesConfiguration(rtVariablesPath + propertiesCacheFileName);
			Log.logInfo(PropertiesCache.class,
					"Created Properties Configuration Object for IO operation of Property Cache File: "
							+ propertiesCacheFileName);
		} catch (Exception e) {
			Log.logError(PropertiesCache.class, "Error Occurred while Creating Properties Configuration Object: "
					+ propertiesCacheFileName + ": " + e.getMessage());
		}
	}

	// Bill Pugh Solution for singleton pattern
	public static class LazyHolder {
		public static PropertiesCache INSTANCE = new PropertiesCache();
	}

	public static PropertiesCache getInstance() {
		return LazyHolder.INSTANCE;
	}

	public static String getProperty(String key) throws Exception {
		return (String) propertiesConfig.getProperty(key);
	}

	public Iterator<String> getAllPropertyNames() {
		return propertiesConfig.getKeys();
	}

	public boolean containsKey(String key) {
		return propertiesConfig.containsKey(key);
	}

	public static void setCacheProperty(String key, String value) throws Exception {
		PropertiesCache.getInstance();
		propertiesConfig.setProperty(key, value);
		propertiesConfig.save();
		Log.logInfo(PropertiesCache.class, "New Properties Cache Log file is created: " + propertiesCacheFileName);
	}

	public static void createPropertiesCacheFile() throws Exception {

		File createFile = new File(rtVariablesPath + propertiesCacheFileName);
		if (createFile.createNewFile()) {
			Log.logInfo(PropertiesCache.class, "New Properties Cache Log file is created: " + propertiesCacheFileName);

		} else {
			Log.logInfo(PropertiesCache.class, "Properties Cache Log file already exists: " + propertiesCacheFileName);
		}
	}

}