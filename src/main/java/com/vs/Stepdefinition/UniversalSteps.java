
package com.vs.Stepdefinition;

import java.io.StringWriter;
import java.util.ArrayList;

import com.vs.config.PropertiesCache;
import com.vs.executor.CukeExecutor;
import com.vs.libraries.GenericLib;
import com.vs.libraries.GlobalVariables;
import com.vs.libraries.Log;

import cucumber.api.java.en.Given;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UniversalSteps {

	public String currentPageName;

	StringWriter sw = new StringWriter();
	public static ArrayList<Object> arrayList = new ArrayList<Object>();

	public ArrayList<Object> getParamValues(String params) throws Exception {
		arrayList.clear();
		String str[] = params.split(",");
		for (int i = 0; i < str.length; i++) {
		// if (params.startsWith("Env") || params.startsWith("env")) { //Sahil K
		if (str[i].startsWith("Env") || str[i].startsWith("env")) {
		try {
		// String keyValue = PropertiesCache.getProperty(params.substring(3, params.length())).trim(); //Sahil K
		String keyValue = PropertiesCache.getProperty(str[i].substring(3, str[i].length())).trim();
		arrayList.add(keyValue);
		} catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
		}
		} else {
		arrayList.add(str[i]);
		}
		}
		return arrayList;
		}

	public String getParamObjects(String params) throws Exception {

		// String

		String elementObject;
		String str[] = params.split(",");
		elementObject = str[0].trim();
		
		if (str.length > 0) {

			for (int i = 1; i <= str.length - 1; i++) {

				

				if (str[i].startsWith("Env")) {

					try {
						GlobalVariables.paramsVal[i-1] = PropertiesCache.getProperty(str[i].substring(3, str[i].length()))
                                .trim();
						// arrayList.add(keyValue);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				} else {
					// System.out.println(str[0]);
					GlobalVariables.paramsVal[i - 1] = str[i].trim();

				}
			}
		}
		return elementObject;
	}

	public void invokeMethod(String pageName, String methodName, ArrayList<Object> args) throws Exception {
		if (methodName.equals("navigateTo")) {
			currentPageName = pageName;
		}
		
		Class PageClass = Class.forName("com.epp.zelle.pages." + pageName);
		// Create a Object of Page
		Object PageObject = PageClass.newInstance();
		// Invoke the method
		Method objMethod = PageClass.getMethod(methodName, null);
		objMethod.invoke(PageObject);
		// return await Reflect.apply(Reflect.get(PageObject, method), PageObject,
		// args);
	}

	public void invokeElementMethod(String element, String action, ArrayList args) throws Exception {

		// try {

		// Dynamically Import the Page from pages folder
		//GlobalVariables.paramsVal= new String[10];
		Class PageClass = Class.forName("com.epp.zelle.pages." + currentPageName);
		Object PageObject = PageClass.newInstance();
		Field ElementObject = PageClass.getField(element);
		Method objMethod = ElementObject.get(PageObject).getClass().getMethod(action, ArrayList.class);
		objMethod.invoke(ElementObject.get(PageObject), args);
	}


	@Given("^User open the \"([^\"]*)\" url$")
	public void user_open_the_url(String urlname) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		String url = PropertiesCache.getInstance().getProperty(urlname).trim();
		GenericLib.openURL(url);
		Log.logInfo(CukeExecutor.class, "Url has Opened");
	    //throw new PendingException();
	}
}
