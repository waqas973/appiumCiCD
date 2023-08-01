package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;
import com.qa.MenuPage;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends MenuPage{
	

	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView" ) private WebElement productTextElem;
	@AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]" ) private WebElement SLBTitleElem;
	@AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]" ) private WebElement SLBPriceElem;

	public String getText() {
		return getAttribute(productTextElem, "text");
		
	}
	public String getSLBTitle() {
		return getAttribute(SLBTitleElem, "text");
		
	}
	public String getSLBPrice() {
		return getAttribute(SLBPriceElem, "text");
		
	}
	public ProductDetailsPage clickOnSLBTitle() {
		clickElem(SLBTitleElem);
		 return new ProductDetailsPage();
		
	}
	
}
