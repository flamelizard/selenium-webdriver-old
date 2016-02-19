package com.selenium.tests;

/**
 * Created by Tom on 1/15/2016.
 */

import com.selenium.kurzyCzPrevodnikMenPageV2;
import com.selenium.xeCurrencyConverterPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/* Page Object is Selenium design pattern for building maintainable automation*/
public class TestPageObjects {
    WebDriver driver;
    //    String url = "http://www.xe.com/currencyconverter/";
    String url = "http://www.kurzy.cz/kurzy-men/prevodnik-men/";


    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "./src/com/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(url);
    }

    //    @Test
    public void currencyConverter() {
        xeCurrencyConverterPage converter = new xeCurrencyConverterPage(driver);
        converter.setAmount(100);
//        converter.setFrom("SGD - Singapore Dollar");
//        converter.setTo("INR");
        converter.convertAmount();
        String amount = converter.getResult();

//        trivial assert
        Assert.assertTrue("Amount is greater than 0",
                Double.valueOf(amount) > 0);
    }

    @Test
    public void encapsulatedKurzyCzPrevodnik() {
        kurzyCzPrevodnikMenPageV2 converter = new kurzyCzPrevodnikMenPageV2();
        converter.load();
        Double amount2 = converter.convertAmountFromTo(100, "EUR", "INR");
        converter.close();
        Assert.assertTrue("Conversion matches", amount2 > 1000);
        System.out.println("[convertAmount] " + amount2);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
