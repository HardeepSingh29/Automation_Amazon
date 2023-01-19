package com.vs.pages;

import com.vs.pages.BasePage;
import com.vs.webelement.Label;
import com.vs.initdriver.RDPDriver;
import com.vs.libraries.GlobalVariables;
import com.vs.webelement.Button;

public class LandingPage extends BasePage {

	public Button btnGetStarted = new Button("id", "ContinueButton");
	
	// public Button btnGetStarted = new Button("xpath", "//button[@id='"+GlobalVariables.paramsVal[0]+"']");
	
	
	public Button btnCarouselViewButton1 = new Button("xpath",
			"//div[contains(@class,'carouselViewButton') and @value='1']");
	public Button btnCarouselViewButton2 = new Button("xpath",
			"//div[contains(@class,'carouselViewButton') and @value='2']");
	public Button btnCarouselViewButton3 = new Button("xpath",
			"//div[contains(@class,'carouselViewButton') and @value='3']");
	public Button btnCarouselViewButton4 = new Button("xpath",
			"//div[contains(@class,'carouselViewButton') and @value='4']");
	public Label lblSafelySendMoneyCarosuel3 = new Label("xpath", "//div[contains(text(),'Safely')]");


	public LandingPage() {
		// //wait till the Issuer ID admin Product information page is loaded
		super("", "WelcomeToZelle", 50000, "id");
	}

// **** Methods ***	

	public void navigateTo() {
		// throw new Error("Method not implemented.");
//		RDPDriver.getDriver().get(
//			"https://epayments-epayui-fnc-1.readiness.billdomain.com/TestPeoplePay50202X/DebugAccess?userName=DUMMY!&productCode=Zelle&destination=Home");
//		System.out.println("Landing page navigateTo");
//		System.out.println("Welcome new framework");		
	}
}