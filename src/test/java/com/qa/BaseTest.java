package com.qa;

import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerAdapter;
import com.qa.utils.TestUtils;



import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import org.apache.commons.codec.binary.Base64;


import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

import java.util.HashMap;

import java.util.Properties;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

@Listeners(ExtentITestListenerAdapter.class)
public class BaseTest {
 protected static	AppiumDriver driver;
 protected static   Properties props;
 protected static String dateTime;
 protected static HashMap<String , String> strings = new HashMap<String , String>();
 protected static AppiumDriverLocalService server;
 
 static Logger log = LogManager.getLogger(BaseTest.class.getName());
 TestUtils testUtils;

 public AppiumDriver getDriver() {
	  return driver;
 }
 public void setDriver(AppiumDriver driver2) {
	   driver = driver2; 
}
 
 public Properties getProps() {
	  return props;
}
public void setProps(Properties props2) {
	   props = props2; 
}
 public String  getDateTime() {
	  return dateTime;
 }
 public void  setDateTime(String datetime2) {
	 dateTime = datetime2;
}
 public HashMap<String , String>  getStrings() {
	  return strings;
}
public void  setStrings(HashMap<String , String> strings2) {
	 strings = strings2;
}
 
  public BaseTest() {
	  PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
  }
  
  @BeforeMethod
  public void beforeMethod() 
  {
	    ((CanRecordScreen) getDriver()).startRecordingScreen();
  }
  
  @AfterMethod
  public void AfterMethod(ITestResult result) {
	 String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();
	  
	  HashMap <String , String> params =   (HashMap<String, String>) result.getTestContext().getCurrentXmlTest().getAllParameters();
		
	    String dir = "videos" +   File.separator +  params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName") + File.separator +  getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();
		
	    File videoDr = new File(dir);
	    
	    if(!videoDr.exists()) {
	    	videoDr.mkdirs();
	    }
	    
	    try {
	    	  FileOutputStream stream = new FileOutputStream(videoDr + File.separator + result.getName() + ".mp4");
	    	  stream.write(Base64.decodeBase64(media));
	    }
	    catch(FileNotFoundException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	  
  }
  
  @BeforeSuite
  public void beforeSuite() {
	server =  getAppiumServerCustom();
	server.start();
	server.clearOutPutStreams();
  }
  @AfterSuite
  public void afterSuite() {
	server.stop();
  }
  
  
//  public AppiumDriverLocalService getAppiumServerDefault() {
//	return  AppiumDriverLocalService.buildDefaultService();
//  }
  
  public AppiumDriverLocalService getAppiumServerCustom() {
		return  AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
				.withAppiumJS(new File("C:\\Users\\WORLD\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE  ));
	  }
  
  
  @Parameters({"platformName","uDID","platformVersion","deviceName"})
  @BeforeTest
  public void beforeTest(String platformName ,  String uDID, String platformVersion, String deviceName ) throws Exception {
	  testUtils = new TestUtils();
	  setDateTime( testUtils.getDateTime());
	  InputStream inputStream = null;
	  InputStream stringsis = null;
	  Properties props = new Properties();
	  
	  log.error("This is error");
	  log.warn("This is error");
	  log.info("This is error");
	  log.debug("This is error");
	try {
		  props = new Properties();
		  String propFileName  = "config.properties";
		  String stringName = "strings/strings.xml";
		  
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		  stringsis = getClass().getClassLoader().getResourceAsStream(stringName);
		  props.load(inputStream);
		  setProps(props);
		  
		  setStrings(testUtils.parseStringXML(stringsis));
		  
		  DesiredCapabilities caps = new DesiredCapabilities();

	      caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
	      caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("andriodAutomationName"));
	      caps.setCapability(MobileCapabilityType.UDID, uDID);
	      caps.setCapability("appActivity", props.getProperty("andriodAppActivity"));
	      caps.setCapability("appPackage", props.getProperty("androidAppPackage"));

	      String appPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" +  File.separator + props.getProperty("androidAppLocation");
	      caps.setCapability(MobileCapabilityType.APP, appPath);

	      URL url = new URL(props.getProperty("appiumUrl"));
	      setDriver(new AndroidDriver(url,caps));
	   
	      
	  }catch(Exception e) {
		e.printStackTrace();
		throw e;
	  }finally{
		  if(inputStream != null) {
			  inputStream.close();
		  }
		  if(stringsis != null) {
			  stringsis.close();
		  }
	  }
	 
  }

  public void waitForVisibility(WebElement elem) {
	    WebDriverWait wait = new WebDriverWait(getDriver() ,Duration.ofSeconds(TestUtils.WAIT));
	    wait.until(ExpectedConditions.visibilityOf(elem));
  }
  public void clickElem(WebElement elem) {
	  waitForVisibility(elem);
	  elem.click();
}
  public void sendKeys(WebElement elem,String txt) {
	  waitForVisibility(elem);
	  elem.sendKeys(txt);
}
  public String getAttribute(WebElement elem,String attrName) {
	  waitForVisibility(elem);
	  	  return elem.getAttribute(attrName);

}
  
 
  public WebElement scrollToElement() {
	return   getDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()" + ".description(\"test-Inventory item page\")).scrollIntoView(" + "new UiSelector().description(\"test-Price\"))"));
  }
  public void closeApp() {
	  
	  ((InteractsWithApps) getDriver()).terminateApp(props.getProperty("androidAppPackage"));
  }
  public void launchApp() {
	  ((InteractsWithApps) getDriver()).activateApp(props.getProperty("androidAppPackage"));
  }
  
  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
