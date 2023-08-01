package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;
import com.qa.MenuPage;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductDetailsPage extends MenuPage{
	

	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]" ) private WebElement SLBTitleElem;
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]" ) private WebElement SLBDescElem;
	@AndroidFindBy (accessibility = "test-Price" ) private WebElement SLBPriceElem;
	@AndroidFindBy (accessibility = "test-BACK TO PRODUCTS" ) private WebElement goBackElem;
	
	
	public String getSLBTitle() {
		return getAttribute(SLBTitleElem, "text");
		
	}
	public String getSLBDesc() {
		return getAttribute(SLBDescElem, "text");
		
	}
	public String getSLBPrice() {
		return getAttribute(SLBPriceElem, "text");
		
	}

	public ProductsPage backToProductPage() {
		clickElem(goBackElem);
		return new ProductsPage();
		
	}
	public ProductDetailsPage scrollToSLBPrice() {
		scrollToElement();
		return this;
		
	}
	
	
}
