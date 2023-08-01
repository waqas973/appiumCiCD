package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest{
	
	@AndroidFindBy (accessibility =  "test-Username") private WebElement userNameElem;
	@AndroidFindBy (accessibility =  "test-Password") private WebElement passwordElem;
	@AndroidFindBy (accessibility = "test-LOGIN" ) private WebElement loginElem;
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView" ) private WebElement errorText;

	public LoginPage enterUsername(String userName) {
		sendKeys(userNameElem, userName);
		return this;
		
	}
	
	public LoginPage enterPassword(String password) {
		sendKeys(passwordElem, password);
		return this;
		
	}
	public ProductsPage clickLoginBtn() {
		clickElem(loginElem);
		return new ProductsPage();
		
	}
	
	public ProductsPage login(String userName , String password) {
		enterUsername(userName);
		enterPassword(password);
		return clickLoginBtn();
		
	}
	public String getErrText() {
		return getAttribute(errorText, "text");
		
	}
}
