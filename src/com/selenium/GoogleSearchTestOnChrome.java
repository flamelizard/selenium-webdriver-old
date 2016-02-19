package com.selenium;

/**
 * Created by Tom on 1/8/2016.
 */

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

// First simple class using Selenium WebDriver
public class GoogleSearchTestOnChrome {

    private WebDriver driver;

    public static void printElementInfo(WebElement elem) {
        String elemInfo = elem.getTagName() + "," + elem.getText() + "," +
                " " + elem.getAttribute("class") + ", " + elem
                .getAttribute("id") + ", " + elem.getLocation();
        System.out.println("[element info] " + elemInfo);
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "./src/com/resources/chromedriver.exe");

        // Launch Chrome
        driver = new ChromeDriver();
        // Maximize the browser window
//        driver.manage().window().maximize();
        // Navigate to Google
        driver.get("http://www.google.com");
    }

    @Test
    public void testGoogleSearch() {
//        Find the text input element by its name
//        In this case, <input ... name="q" ...
        WebElement element = driver.findElement(By.name("q"));
//        printElementInfo(element);

        // Enter something to search for
        element.sendKeys("Selenium testing tools cookbook");

        // Now submit the form. WebDriver will find
        // the form for us from the element
        element.submit();

        // Google's search is rendered dynamically with JavaScript
        // Wait for the page to load, timeout after 10 seconds
        new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase()
                        .startsWith("selenium testing tools cookbook");
            }
        });

        String searchEng = "Selenium testing tools cookbook - Google Search";
        String searchCze = "Selenium testing tools cookbook - Hledat Googlem";
        Assert.assertTrue(driver.getTitle().equals(searchCze) || driver
                .getTitle().equals(searchEng));
//        Assert.assertEquals("Selenium testing tools cookbook - Google " +
//                "Search", driver.getTitle());

    }

    @After
    public void tearDown() throws Exception {
        // Close the browser
        driver.quit();
    }
}