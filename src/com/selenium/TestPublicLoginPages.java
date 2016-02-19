package com.selenium;

import com.opencsv.CSVReader;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Tom on 1/10/2016.
 */

/*
*   Note on using inheritance in JUnit tests
*
*   I initially tried to subclass the SimpleDDT to reuse the code to test
*   LastFM. Briefly after I come across some issue in method / var access
*   control when trying to run the class.
*
*   I found an article explaining why inheritance (subclassing) is NOT
*   recommended for tests.
*
*   The reasons are as follows:
*       worse readability of tests
*       hard maintainability
*       bad test performance
*
*   Inheritance is not right tool for reuse code but to implement polymorphism.
*
*   Source
*   http://www.petrikainulainen.net/programming/unit-testing/3-reasons-why-we-should-not-use-inheritance-in-our-tests/
* */

@RunWith(Parameterized.class)
public class TestPublicLoginPages {
    protected static WebDriver driver;
    static String csvFile =
            "D:\\testing\\selenium-webdriver\\src\\com\\resources\\testDataLastFM.csv";
    String url = "https://secure.last.fm/login";
    private String userName;
    private String password;
    private String expectedResponse;

    public TestPublicLoginPages(String userName, String password, String
            expectedResponse) {
        this.userName = userName;
        this.password = password;
        this.expectedResponse = expectedResponse;
    }

    @Parameterized.Parameters
    public static List<String[]> testData() {
        try {
            return getTestDataCSV(csvFile).subList(0, 2);
        } catch (IOException e) {
//            not great handling
            e.printStackTrace();
            return null;
        }
//        return Arrays.asList(new String[][]{
//                {"bob jones", "bo@jones", "Welcome"},
//                {"", "bob@jones", "Please fill out this field"},
//                {"bob", "+-", "is missing an '@"},
//                {"123", "bob@jones", "match the requested format"}
//        });
    }

    public static List<String[]> getTestDataCSV(String csvFile) throws
            IOException {
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            return reader.readAll();
        }
    }

//    @Before
//    public void setUp() {
////        system var to locate stand-alone chrome driver for test automation
//        System.setProperty("webdriver.chrome.driver",
//                "./src/com/resources/chromedriver.exe");
//
//        driver = new ChromeDriver();
//        driver.manage().window().setSize(new Dimension(200, 1000));
//        driver.get(url);
//    }

    /*
    Headless setup using PhantomJS that is Webkit Javascript API
    However, it relies on driver Ghost that links PhantomJS and Selenium
    and the project hasn't got active community.

    So, GhostDriver seems to be part of phantomJS binary but nobody knows for how
    long.
    */
    @Before
    public void setUpHeadless() {
        System.setProperty("phantomjs.binary.path",
                "d:\\testing\\phantomjs-2.0.0-windows\\bin\\phantomjs.exe");
//        DesiredCapabilities capab = new DesiredCapabilities();
//        capab.setCapability(PhantomJSDriverService
//                .PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
//                "d:\\testing\\phantomjs-2.0.0-windows\\bin\\phantomjs.exe");
        driver = new PhantomJSDriver();
        driver.get(url);
    }

    @Test
    public void testFillForm() {
        WebElement user = driver.findElement(By.id("id_username"));
        user.clear();
        user.sendKeys(this.userName);

        WebElement password = driver.findElement(By.id("id_password"));
        password.clear();
        password.sendKeys(this.password);

        WebElement form = driver.findElement(By.id("login"));
        form.submit();

//        reread, otherwise stale object
        form = driver.findElement(By.id("login"));

//        Compound class elements can get only CSS selector
        String errors = driver.findElement(By.cssSelector("div.alert" +
                ".alert-danger"))
                .getText();
//        System.out.println(errors);

        Matcher matchResult = Pattern.compile(expectedResponse).matcher(form
                .getText());
//        Assert commands will show the actual and expected if it fails
        Assert.assertThat(matchResult.find(), is(true));
        System.out.println("[matched] " + matchResult.group());
//        System.out.println(matchResult.pattern());

//        Assert.assertEquals(driver.getTitle(), "Show form");

    }

    @After
    public void tearDown() throws Exception {
        // Close the browser
        driver.quit();
    }
}

