package com.vs.webelement;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.vs.config.Config;
import com.vs.config.PropertiesCache;
import com.vs.initdriver.RDPDriver;
import com.vs.libraries.GlobalVariables;
import com.vs.libraries.Log;

public class BaseElement {

	protected String locatorValue;
	protected String locatorType;
	protected WebDriver webDriver;

	public static String returnDisplayText = null;

	static WebDriverWait webDriverwait = new WebDriverWait(RDPDriver.getDriver(), Config.objectWaitTime);

	public BaseElement(String locatorType, String locatorValue) {
		this.locatorType = locatorType;
		this.locatorValue = locatorValue;
		webDriver = RDPDriver.getDriver();

	}

	public WebElement get() throws Exception {
		switch (this.locatorType.toLowerCase()) {
		case "css":
			return webDriver.findElement((By.cssSelector(this.locatorValue)));
		case "classname":
			return webDriver.findElement((By.className(this.locatorValue)));
		case "linktext":
			return webDriver.findElement((By.linkText(this.locatorValue)));
		case "tagname":
			return webDriver.findElement((By.tagName(this.locatorValue)));
		case "xpath":
			return webDriver.findElement((By.xpath(this.locatorValue)));
		case "name":
			return webDriver.findElement((By.name(this.locatorValue)));
		case "partiallinktext":
			return webDriver.findElement((By.partialLinkText(this.locatorValue)));
		case "id":
			return webDriver.findElement((By.id(this.locatorValue)));
//        case "model":
//            return webDriver.findElement((By.model(this.locatorValue)));
		case "text":
			return webDriver.findElement((By.xpath("//*[text()='${this.locatorValue}']")));
//        case "partialbuttontext":
//            return webDriver.findElement((By.partial(this.locatorValue)));
		default:
			return webDriver.findElement((By.id(this.locatorValue)));
		}
	}

	public void validateInnerText(ArrayList<Object> validationText) throws Exception {

		String actInnerText = this.get().getText().trim();
		Object objOne = validationText.get(0);
		
		if( objOne.equals((PropertiesCache.getProperty("mobileNumberOne").trim()))) {
			objOne = ((String) objOne).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
		}
		String expInnerText = objOne.toString();
		if (validationText.size() > 1) {
			Object objTwo = validationText.get(1);
			boolean secondParam = Boolean.parseBoolean(objTwo.toString());

			if (!secondParam) {
				if (!actInnerText.contains(expInnerText)) {
					GlobalVariables.testStatus = true;
					Log.logInfo(BaseElement.class, "Excepted : " + expInnerText
							+ " Inner text not matched in Actual inner text : " + actInnerText);
				} else {
					GlobalVariables.testStatus = false;
					Log.logError(BaseElement.class, "Excepted : " + expInnerText
							+ " Inner text matched in Actual inner text : " + actInnerText);
				}
			} else {
				if (actInnerText.contains(expInnerText)) {
					GlobalVariables.testStatus = true;
					Log.logInfo(BaseElement.class, "Excepted : " + expInnerText
							+ " Inner text matched in Actual inner text : " + actInnerText);
				} else {
					GlobalVariables.testStatus = false;
					Log.logError(BaseElement.class, "Excepted : " + expInnerText
							+ " Inner text not matched in Actual inner text : " + actInnerText);
				}
			}

		} else {

			if (actInnerText.contains(expInnerText)) {
				GlobalVariables.testStatus = true;
				Log.logInfo(BaseElement.class, "Inner text matched. \n\t" + actInnerText + "\n\t" + expInnerText);
			} else {
				GlobalVariables.testStatus = false;
				Log.logError(BaseElement.class,
						"Inner text matched failed. \n\t" + actInnerText + "\n\t" + expInnerText);
			}

		}
	}

	public String getJSText() {

		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		// js.executeScript(, this.getDisplayeddata().toString())
		return "";
		// return Innertext;
	}

	public void elementIsDisabled(ArrayList<Object> booleanFlag) throws Exception {
		Object obj = booleanFlag.get(0);
		boolean flag = Boolean.parseBoolean(obj.toString());
		boolean isDisabledVar = false;
		String disabledAttr = this.get().getAttribute("disabled");
		String classAttr = this.get().getAttribute("class");

		if (disabledAttr.length() > 0) {
			isDisabledVar = true;
		}
		if (!isDisabledVar) {
			if (classAttr.length() > 0) {
				if (disabledAttr.indexOf("disabled") > 0) {
					isDisabledVar = true;
				}
			}
		}
		if (flag) {
			if (isDisabledVar) {
				GlobalVariables.testStatus = true;
				Log.logInfo(BaseElement.class, "Element:" + this.locatorValue + "is disabled in the screen");
			} else {
				GlobalVariables.testStatus = false;
				Log.logError(BaseElement.class, "Element:" + this.locatorValue + "is not disabled in the screen");
			}
		} else {
			if (!isDisabledVar) {
				GlobalVariables.testStatus = true;
				Log.logInfo(BaseElement.class, "Element:" + this.locatorValue + "is enabled in the screen");
			} else {
				GlobalVariables.testStatus = false;
				Log.logError(BaseElement.class, "Element:" + this.locatorValue + "is not enabled in the screen");
			}
		}

	}

	/**
	 * Name: validateAttributeValue
	 * Description: Method is fetching attribute value and matches with the actual inner text
	 * @author Gundeep Singh
	 * @param Arraylist objects 
	 * Date: 28/04/2020
	 */
	
	public void validateAttributeValue(ArrayList<Object> arrayList) throws Exception {
		String attrInnerValue = null;
		if (this.get().isDisplayed()) {
			String attribute = arrayList.get(0).toString();
			String value = arrayList.get(1).toString();
			String flag = arrayList.get(2).toString();
			attrInnerValue = this.get().getAttribute(attribute);
			 GlobalVariables.testStatus = true;
			 Log.logInfo(BaseElement.class, "BaseElement: Attribute found" + attribute + "for the object"
					+ this.locatorType + "--" + this.locatorValue);
			if(Boolean.parseBoolean(flag)) {
				if (attrInnerValue.trim().equalsIgnoreCase(value.trim()) ) {
					GlobalVariables.testStatus = true;
					Log.logInfo(BaseElement.class, "BaseElement: Attribute Inner value  matched." + "\\n\\t"
							+ attrInnerValue + "\\n\\t" + "---" + value);
	
				} else {
					GlobalVariables.testStatus = false;
					Log.logError(BaseElement.class, "BaseElement: Attribute Inner value is not matched." + "\\n\\t"
							+ attrInnerValue + "\\n\\t" + "---" + value);
				}
			}
			else {
				if (!attrInnerValue.trim().contentEquals(value.trim()) ) {
					GlobalVariables.testStatus = true;
					Log.logInfo(BaseElement.class, "BaseElement: Attribute Inner value does not have" + "\\n\\t"
							+ attrInnerValue + "\\n\\t" + "---" + value);
	
				} else {
					GlobalVariables.testStatus = false;
					Log.logError(BaseElement.class, "BaseElement: Attribute Inner value is matched." + "\\n\\t"
							+ attrInnerValue + "\\n\\t" + "---" + value);
				}
			}
		} 
		 else {
			GlobalVariables.testStatus = false;
			Log.logError(BaseElement.class,
					"BaseElement: Unable to find locator ." + this.locatorType + "--" + this.locatorValue);

		}

	}
	
//    public void validateAttributeValue(ArrayList<Object> arrayList) throws Exception {
//        String attrInnerValue = null;
//        if (this.get().isDisplayed()) {
//
// 
//
//            String attribute = arrayList.get(0).toString();
//            String value = arrayList.get(1).toString();
//
// 
//
//            attrInnerValue = this.get().getAttribute(attribute);
//            GlobalVariables.testStatus = true;
//            Log.logInfo(BaseElement.class, "BaseElement: Attribute found" + attribute + "for the object"
//                    + this.locatorType + "--" + this.locatorValue);
//
// 
//
//            if (attrInnerValue.trim().equalsIgnoreCase(value.trim())) {
//                GlobalVariables.testStatus = true;
//                Log.logInfo(BaseElement.class, "BaseElement: Attribute Inner value  matched." + "\\n\\t"
//                        + attrInnerValue + "\\n\\t" + "---" + value);
//
// 
//
//            } else {
//                GlobalVariables.testStatus = false;
//                Log.logError(BaseElement.class, "BaseElement: Attribute Inner value is not matched." + "\\n\\t"
//                        + attrInnerValue + "\\n\\t" + "---" + value);
//            }
//        } else {
//            GlobalVariables.testStatus = false;
//            Log.logError(BaseElement.class,
//                    "BaseElement: Unable to find locator ." + this.locatorType + "--" + this.locatorValue);
//
// 
//
//        }
//
// 
//
//    }
	
	
	public void getDisplayedText() throws Exception {

		returnDisplayText = this.get().getText();
		Log.logInfo(BaseElement.class, "Get display Text : " + returnDisplayText);
		GlobalVariables.testStatus = true;

	}

	public String getDisplayeddata() throws Exception {

		String returnDisplayData = null;
		returnDisplayData = this.get().getAttribute("value");
		GlobalVariables.testStatus = true;
		return returnDisplayData;

	}

//	public boolean isDisplayed() {
//
//		try {
//			return this.get().isDisplayed();
//		} catch (Exception e) {
//			return false;
//		}
//	}
	public void waitfor() throws Exception {
		if (webDriverwait.until(ExpectedConditions.visibilityOf(this.get())) != null) {
			GlobalVariables.testStatus = true;
			Log.logInfo(BaseElement.class, "Waited till " + this.locatorValue + " is present in screen");
		} else {
			GlobalVariables.testStatus = false;
			Log.logError(BaseElement.class,
					"Action to wait till " + this.locatorValue + " is displayed in screen failed");
		}
	}

	public void clearTextField() throws Exception {
		this.get().clear();

		Log.logInfo(BaseElement.class, "Text of " + this.locatorValue + " is cleared");
		GlobalVariables.testStatus = true;
	}

	public void elementIsPresent(ArrayList<Object> booleanFlag) throws Exception {
		Object obj = booleanFlag.get(0);
		boolean flag = Boolean.parseBoolean(obj.toString());
		if (flag) {
			if (webDriverwait.until(ExpectedConditions.visibilityOf(this.get())) != null) {
				Log.logInfo(BaseElement.class, "Element" + this.locatorValue + " is present/displayed in screen ");
				GlobalVariables.testStatus = true;
			} else {

				Log.logError(BaseElement.class, "Element" + this.locatorValue + " is not present/displayed in screen ");
				GlobalVariables.testStatus = false;
			}
		} else {
			try {

				if (webDriverwait.until(ExpectedConditions.invisibilityOf(this.get())) != null) {

					Log.logInfo(BaseElement.class,
							"Element" + this.locatorValue + " is not present/displayed in screen ");
					GlobalVariables.testStatus = true;
				} else {

					Log.logError(BaseElement.class, "Element" + this.locatorValue + " is present/displayed in screen ");
					GlobalVariables.testStatus = false;
				}

			} catch (Exception e) {

				// e.printStackTrace();
				if (e.getMessage().contains("no such element")) {
					Log.logInfo(BaseElement.class,
							"Element" + this.locatorValue + " is not present/displayed in screen ");
					GlobalVariables.testStatus = true;
				} else {
					Log.logError(BaseElement.class,
							"Element" + this.locatorValue + " Exception while checking invisibility of element ");
					GlobalVariables.testStatus = false;
				}

				// TODO: handle exception
			}

		}

	}

	/**
	 * Name: getElements
	 * Description: Method is fetching list of elements from the xpath
	 * @author Gundeep Singh
	 * @param list of Webelements - 
	 * Date: 28/04/2020
	 */
	
	public List<WebElement> getElements() throws Exception {
		switch (this.locatorType.toLowerCase()) {
		case "xpath":
			return webDriver.findElements((By.xpath(this.locatorValue)));
		default:
			return webDriver.findElements((By.xpath(this.locatorValue)));
		}
	}
	
}
