package com.qa.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.gson.JsonObject;
import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;

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

public class ProductsTest extends BaseTest {
	LoginPage loginPage;
	ProductsPage productsPage;
	SettingsPage settingsPage;
	ProductDetailsPage productDetailsPage;
   
    JSONObject loginUsers;
    
    
  @BeforeMethod
  public void beforeMethod() {
	  loginPage = new LoginPage();
	  loginPage.enterUsername(loginUsers.getJSONObject("validUser").getString("userName"));
	  loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
      productsPage = loginPage.clickLoginBtn();
  }

  @AfterMethod
  public void afterMethod() {
	  settingsPage =   productsPage.clickOnMenu();
      loginPage = settingsPage.clickOnLogout();
     
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
  public void validProductOnProductsPage() {
           SoftAssert sa = new SoftAssert();
		 
       String SLBTitle =   productsPage.getSLBTitle();
       String SLBPrice =   productsPage.getSLBPrice();
       sa.assertEquals(SLBTitle, getStrings().get("product_page_slb_title"));
       sa.assertEquals(SLBPrice, getStrings().get("product_page_slb_price"));
       
      
       sa.assertAll();

		 
	  }  
  @Test
  public void validProductOnProductDetailPage() {
           SoftAssert sa = new SoftAssert();
		 
          productDetailsPage =  productsPage.clickOnSLBTitle();
          
       String SLBTitle =   productDetailsPage.getSLBTitle();
       String SLBdesc =   productDetailsPage.getSLBDesc();
       String SLBPrice =   productDetailsPage.getSLBPrice();     
       
       sa.assertEquals(SLBTitle, getStrings().get("product_page_slb_title"));
       sa.assertEquals(SLBdesc, getStrings().get("product_page_slb_desc"));
       
       productDetailsPage.scrollToSLBPrice(); 
       sa.assertEquals(SLBPrice, getStrings().get("product_page_slb_price"));
     
       sa.assertAll();

		 
	  }  

}
