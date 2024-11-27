package com.fitpio.project.pageobjects;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonsActions extends BasePage{
    public static String screenshotPath;

    public CommonsActions(WebDriver driver) {
        super(driver);
    }

    public void scrollElementIntoViewport(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void setElementAttributeValue(WebElement element, String attributeName, String attributeValue) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, attributeName,
                attributeValue);
    }

    public void refreshBrowser() {
        try {
            driver.navigate().refresh();
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void navigateToUrl(String url) {
        driver.navigate().to(url);
    }

    public void getApplication(String url) {
        driver.get(url);
    }

    public void clickElementUsingJavascript(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void captureScreenshots() {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            String screenshotName = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()).replaceAll("[^0-9]", "") + ".jpg";
            String screenshotDirectory = System.getProperty("user.dir") + "//target//screenshots//";
            screenshotPath = screenshotDirectory + screenshotName;
            FileUtils.copyFile(screenshot,new File(screenshotPath));
        } catch (Exception e) {
            System.out.println("Exception occurred while capturing the screenshot:" + e.getMessage());
        }
    }

}
