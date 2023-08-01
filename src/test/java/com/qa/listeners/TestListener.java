package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.qa.BaseTest;

public class TestListener implements ITestListener {

	public void onTestFailure(ITestResult result) {
		if(result.getThrowable() != null) {
			 StringWriter wr = new StringWriter();
			  PrintWriter pr = new PrintWriter(wr);
			  result.getThrowable().printStackTrace(pr);
			  System.out.println(wr);
		}
		
		BaseTest baseTest = new BaseTest();
	File file =	baseTest.getDriver().getScreenshotAs(OutputType.FILE);
	
	Map <String , String> params = new HashMap<String , String>();
	params =   result.getTestContext().getCurrentXmlTest().getAllParameters();
	
    String imagePath = "Screenshots" +   File.separator +  params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName") + File.separator +  baseTest.getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";
	
    String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;
    
    try {
		FileUtils.copyFile(file, new File(imagePath));
		Reporter.log("<a href='"+ completeImagePath + "'><img scr='"+ completeImagePath + "' height='100' width='100' /></a>");
	}catch(IOException e) {
		e.printStackTrace();
	}
	
	}
}
