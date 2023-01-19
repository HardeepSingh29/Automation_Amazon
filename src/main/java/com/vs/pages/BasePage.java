package com.vs.pages;

import com.vs.webelement.BaseElement;

public abstract class BasePage {

	public String pageURL;
	public int timeOut;
	public boolean useDefaultTimeout = false;
	// public String xpathValidator ;
	public BaseElement xPathValidator;
	public static boolean isOpenFlag;
	
	public BasePage() {
		super();
	}
	
	public BasePage(String pageURL,String xPathValidator,int timeOut,String locatorType) {
		this.pageURL = pageURL;	   
		//locatorType= (locatorType == locatorType) ? locatorType : "xpath";		
		this.xPathValidator = new BaseElement(locatorType,xPathValidator);
		this.timeOut = 200000;
	}

	abstract void navigateTo();

	public void isOpen() throws Exception {
		isOpenFlag=  this.xPathValidator.get().isDisplayed();
  
	}
}
