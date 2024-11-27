package com.fitpio.project.pageobjects;
import org.openqa.selenium.WebDriver;

import java.util.Objects;


public class FitPioHomePage extends CommonsActions{

	public FitPioHomePage(WebDriver driver) {
		super(driver);
	}

	public boolean verifyHomePage() {
		return Objects.requireNonNull(driver.getTitle()).contains("Remote Patient Monitoring");
	}



}
