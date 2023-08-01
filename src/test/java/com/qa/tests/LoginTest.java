package com.qa.tests;

import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;

import io.appium.java_client.AppiumBy;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTest extends BaseTest {
	LoginPage loginPage;
	ProductsPage productsPage;
    JSONObject loginUsers;
    
    
  @BeforeMethod
  public void beforeMethod() {
	  loginPage = new LoginPage();
  }

  @AfterMethod
  public void afterMethod() {
  }

  @BeforeClass
  public void beforeClass() throws IOException {
	  InputStream datais = null;
	  try {
		  String dataFileName = "data/loginUsers.json";
		  datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
		  JSONTokener tokener = new JSONTokener(datais);
		  loginUsers = new JSONObject(tokener);
	  }catch(Exception e) {
		  e.printStackTrace();
	  }finally {
		  if(datais != null) {
			  datais.close();
		  }
	  }
	  
	  closeApp();
	  launchApp();
	  
  }

  @AfterClass
  public void afterClass() {
  }
  
  @Test
  public void invalidUserName() {
	 
	  loginPage.enterUsername(loginUsers.getJSONObject("invalidUser").getString("userName"));
	  loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
	  loginPage.clickLoginBtn();
	
	 String actualErrorText = loginPage.getErrText();
	
	 String expectedErrorText = getStrings().get("err_invalid_username_or_password");
	 Assert.assertEquals(actualErrorText, expectedErrorText);
	 
	 
  }
  
  @Test
  public void invalidPassword() {
	  
		  loginPage.enterUsername(loginUsers.getJSONObject("invalidPassword").getString("userName"));
		  loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
		  loginPage.clickLoginBtn();
		
		 String actualErrorText = loginPage.getErrText();
		 String expectedErrorText = getStrings().get("err_invalid_username_or_password");
		 Assert.assertEquals(actualErrorText, expectedErrorText); 
		 
	  }  
  @Test
  public void successfullLogin() throws InterruptedException {

		  loginPage.enterUsername(loginUsers.getJSONObject("validUser").getString("userName"));
		  loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
	      productsPage = loginPage.clickLoginBtn();
		
		 
		 String actualProductText = productsPage.getText(); 
		 String expectedProductText = getStrings().get("product_title");	 
		 Assert.assertEquals(actualProductText, expectedProductText);
		 
	  }  

}
