package com.vs.webelement;

import java.util.ArrayList;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.vs.config.Config;
import com.vs.initdriver.RDPDriver;
import com.vs.libraries.GlobalVariables;
import com.vs.libraries.Log;

public class Button extends BaseElement {

	public Button(String locatorType, String locatorValue) {
		super(locatorType, locatorValue);

	}

	static WebDriverWait webDriverwait = new WebDriverWait(RDPDriver.getDriver(), Config.objectWaitTime);

	public void click(ArrayList<Object> failOnError) throws Exception {

		// Object obj =failOnError.get(0);

		if (webDriverwait.until(ExpectedConditions.visibilityOf(this.get())) != null) {
			if (webDriverwait.until(ExpectedConditions.elementToBeClickable(this.get())) != null) {
				this.get().click();
				
				Log.logInfo(Button.class,
						"Button: Clicked Button with " + this.locatorType + " --- " + this.locatorValue);
				GlobalVariables.testStatus = true;
			} else {
			

				Log.logError(Button.class, "Button: Failed to click Button with " + this.locatorType + " --- "
						+ this.locatorValue + " due to element is not " + "clickable");
				GlobalVariables.testStatus = false;
			}

		} else {
			
			Log.logError(Button.class, "Button: Failed to click Button with " + this.locatorType + " --- "
					+ this.locatorValue + " due to element is not " + "visible");
			GlobalVariables.testStatus = false;

		}

	}
}
