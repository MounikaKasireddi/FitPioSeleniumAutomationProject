package com.fitpio.test;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fitpio.project.pageobjects.FitPioHomePage;
import com.fitpio.project.pageobjects.FitPioRevenueCalcPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    public static ExtentReports extentReport ;
    public static ExtentTest test;

    public WebDriver driver;
    public FitPioHomePage fitPioHome;
    public FitPioRevenueCalcPage fitPioRevenueCalc;

    public static Properties appProperties = new Properties();
    public static String propertiesFilePath = System.getProperty("user.dir") + "/src/test/resources/test.properties";

    @BeforeSuite(alwaysRun = true)
    public void setUpChromeDriver() {
        try {
            appProperties.load(new FileInputStream(propertiesFilePath));
        } catch (Exception e) {
            System.out.println("Exception occurred while reading the properties file" + e.getMessage());
        }
    }

    @BeforeTest(alwaysRun = true)
    public void initializeDriver() {
        extentReport = new ExtentReports();
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"//target//ExtentReports/ExtentReport.html");
        extentReport.attachReporter(extentSparkReporter);
        extentReport.setAnalysisStrategy(AnalysisStrategy.TEST);
        test = extentReport.createTest("FitFioTest").createNode("Test1");
        driver = new ChromeDriver();
        fitPioHome = new FitPioHomePage(driver);
        fitPioRevenueCalc = new FitPioRevenueCalcPage(driver);
    }

    @BeforeTest(alwaysRun = true)
    public void navigateToApplication() {
        fitPioHome.getApplication(appProperties.getProperty("fit.pio.url"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        Assert.assertTrue(fitPioHome.verifyHomePage(), "FitPeo Page is not loaded ");
    }
    @BeforeMethod(alwaysRun = true)
    public void navigateToRevenueCalcPage(){
        fitPioHome.navigateToUrl(appProperties.getProperty("revenue.calc.url"));
        Assert.assertTrue(fitPioRevenueCalc.verifyRevenueCalculatorPage(),
                "Revenue Calculator page is not loaded");
    }
    @AfterMethod(alwaysRun = true)
    public void closeApplication(){
        driver.close();
    }

    @AfterTest(alwaysRun = true)
    public void destroyTheObjects() {
        extentReport.flush();
        driver = null;
        fitPioHome = null;
        fitPioRevenueCalc = null;
    }
}
