package com.vs.webelement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.vs.config.Config;
import com.vs.initdriver.RDPDriver;
import com.vs.libraries.GlobalVariables;
import com.vs.libraries.Log;

import java.util.ArrayList;

import org.openqa.selenium.Keys;

public class SpecialKey extends BaseElement {

 static WebDriverWait webDriverwait = new WebDriverWait(RDPDriver.getDriver(), Config.objectWaitTime);

 public SpecialKey(String locatorType, String locatorValue) {
super(locatorType, locatorValue);

 }

}