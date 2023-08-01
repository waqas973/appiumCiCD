package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class SettingsPage extends BaseTest{
	@AndroidFindBy (accessibility = "test-LOGOUT" ) private WebElement logoutElem;

	public LoginPage clickOnLogout() {
		clickElem(logoutElem);
		return new LoginPage();
	}

}
