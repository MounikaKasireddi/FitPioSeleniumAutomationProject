package com.fitpio.test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.fitpio.project.pageobjects.CommonsActions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class FitPioTest extends BaseTest{

    public void captureScreenshotAndLogToReport(String message){
        fitPioRevenueCalc.captureScreenshots();
//        Reporter.log("<img src=\""+ CommonsActions.screenshotPath +"\" />");
        test.log(Status.PASS, message, MediaEntityBuilder.createScreenCaptureFromPath(CommonsActions.screenshotPath).build());
//        test.addScreenCaptureFromPath(CommonsActions.screenshotPath);

    }


    @Test
    public void fitPioTest1(){
        fitPioRevenueCalc.scrollToSlider();
        captureScreenshotAndLogToReport("Verify scroll To Slider");
        fitPioRevenueCalc.dragSlidePointToGivenNumber("820");
        Assert.assertTrue(fitPioRevenueCalc.verifySliderTextFiledValue("816"));
        captureScreenshotAndLogToReport("verify Slider Text Filed Value");
        fitPioRevenueCalc.refreshBrowser();
        fitPioRevenueCalc.setSliderValue("560");
        Assert.assertTrue(fitPioRevenueCalc.verifyUpdatedSliderPosition("560"));
        captureScreenshotAndLogToReport("verify Updated Slider Position");
        fitPioRevenueCalc.setSliderValue("820");
        fitPioRevenueCalc.selectCPTCheckboxes("CPT-99091,CPT-99453,CPT-99454,CPT-99474");
        Assert.assertTrue(fitPioRevenueCalc.verifyTotalRecurringReimbursementForAllPatientsPerMonthValue("$110700"));
        captureScreenshotAndLogToReport("verify Total Recurring Reimbursement For All Patients Per Month Value");
    }
}
