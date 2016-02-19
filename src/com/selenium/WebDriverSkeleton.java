package com.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/*
*  Minimal code to use Selenium WebDriver with JUnit */
public class WebDriverSkeleton {
    WebDriver driver;
    private String url;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "./src/com/resources/chromedriver.exe");
        driver = new ChromeDriver();
//        driver.manage().window().setSize(new Dimension(100, 100));
        driver.get(url);
    }

    @Test
    public void myTestCase() {
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
