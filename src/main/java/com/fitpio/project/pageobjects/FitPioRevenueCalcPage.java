package com.fitpio.project.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.Objects;

public class FitPioRevenueCalcPage extends CommonsActions {

	public FitPioRevenueCalcPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h4[contains(text(),'Medicare Eligible Patients')]")
	private WebElement medicareEligiblePatientsHeader;

	@FindBy(xpath = "//span[contains(@class,'MuiSlider-root')]")
	private WebElement sliderBarElement;

	@FindBy(xpath = "//span[contains(@class,'MuiSlider-thumb')]")
	private WebElement sliderPointElement;

	@FindBy(xpath = "//input[contains(@class,'MuiInputBase-input') and @type='number']")
	private WebElement sliderButtonTextFiled;

	@FindBy(xpath = "//span[contains(text(),'Patients should be between')]")
	private WebElement sliderRangeElement;

	@FindBy(xpath = "//p[contains(text(),'Total Recurring Reimbursement for all Patients Per Month:')]/p")
	private WebElement totalRecurringReimburseValElement;

	public boolean verifyRevenueCalculatorPage() {
		return Objects.requireNonNull(driver.getCurrentUrl()).contains("revenue-calculator") && medicareEligiblePatientsHeader.isDisplayed();
	}

	public void scrollToSlider() {
		scrollElementIntoViewport(medicareEligiblePatientsHeader);
	}

	public int getSliderPercentageOfGivenNumber(String number) {
		int maxRange = Integer.parseInt(sliderRangeElement.getText().trim().split("to")[1].trim());
		return (Integer.parseInt(number) * 100) / maxRange;

	}

	public void dragSlidePointToGivenNumber(String number) {
		String numberPercentage = String.valueOf(getSliderPercentageOfGivenNumber(number)) + "%";
		Actions actions = new Actions(driver);
		setElementAttributeValue(sliderPointElement, "style", "left: " + numberPercentage);
		try {
			scrollElementIntoViewport(medicareEligiblePatientsHeader);
			Thread.sleep(3000);
			actions.click(sliderPointElement).build().perform();
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getSliderInputValue() {
		return sliderButtonTextFiled.getAttribute("value").trim();
	}

	public boolean verifySliderTextFiledValue(String number) {
		return Integer.parseInt(getSliderInputValue()) == Integer.parseInt(number);
	}

	public void setSliderValue(String number) {
		Actions actions = new Actions(driver);
		Action action = actions.moveToElement(sliderButtonTextFiled).doubleClick(sliderButtonTextFiled)
				.sendKeys(Keys.DELETE).sendKeys(sliderButtonTextFiled, number).build();
		action.perform();
	}

	public boolean verifyUpdatedSliderPosition(String number) {
		String numberPercentage = getSliderPercentageOfGivenNumber(number) + "%";
		return Objects.requireNonNull(sliderPointElement.getAttribute("style")).contains(numberPercentage);
	}

	public void selectCPTCheckboxes(String checkboxNames) {
		for(String eachCheckBox :checkboxNames.split(",")){
			WebElement checkBox = driver.findElement(
					By.xpath("//p[text()='" + eachCheckBox.trim() + "']//following-sibling::label//input[@type='checkbox']"));
			scrollElementIntoViewport(checkBox);
			clickElementUsingJavascript(checkBox);
		}
	}

	public boolean verifyTotalRecurringReimbursementForAllPatientsPerMonthValue(String value) {
		return totalRecurringReimburseValElement.getText().trim().equals(value);
	}

}
