package com.selenium.pageobjects;

/**
 * Created by Tom on 1/21/2016.
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Browser {
    private static WebDriver driver;

    public static WebDriver driver() {
        if (driver == null) {
            throw new NullPointerException("Browser driver not initialized");
        }
        return driver;
    }

    public static void useFirefox() {
        driver = new FirefoxDriver();
    }

    public static void useChrome() {
        //    Path to Chrome driver has to be set beforehand, e.g. in JUnit
        driver = new ChromeDriver();
    }

//    static void initDriver() {
//        System.setProperty("webdriver.chrome.driver",
//                "./src/com/resources/chromedriver.exe");
//        driver = new ChromeDriver();
//
//    }

    public static void open(String url) {
        driver.get(url);
    }

    public static void close() {
        driver.quit();
    }
}
