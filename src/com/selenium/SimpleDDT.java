package com.selenium;

/**
 * Created by Tom on 1/10/2016.
 */

import com.opencsv.CSVReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/* Test suite build with JUnit. Another popular unittest framework is TestNG*/
@RunWith(Parameterized.class)
public class SimpleDDT {
    protected static WebDriver driver;
    static String csvFile =
            "D:\\testing\\selenium-webdriver\\src\\com\\resources\\testData.csv";
    protected String url = "file:///D:/git-repos/web_basic/sample_login_page" +
            ".html";
    protected String userName;
    protected String email;
    protected String expectedResponse;

    //    JUnit will instantiate constructor for each row in @Parameters table
    public SimpleDDT(String userName, String email, String expectedResponse) {
        this.userName = userName;
        this.email = email;
        this.expectedResponse = expectedResponse;
    }

    @Parameters
    public static List<String[]> testData() {
        try {
            return getTestDataCSV(csvFile);
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

    @Before
    public void setUp() {
//        system var to locate stand-alone chrome driver for test automation
        System.setProperty("webdriver.chrome.driver",
                "./src/com/resources/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(50, 50));
        driver.get(url);
    }

    //    test case annotation
    @Test
    public void testFillForm() {
        WebElement user = driver.findElement(By.name("name"));
        user.clear();
        user.sendKeys(this.userName);

        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys(this.email);

        WebElement form = driver.findElements(By.tagName("form")).get(0);
        form.submit();

        WebDriverWait wait = new WebDriverWait(driver, 3);
//        Fluent wait will poll the page for specified times, useful for
//        dynamically load content like AJAX, however unnecessary to detect
//        DOM manipulation through javascript

//        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//                .withTimeout(2, TimeUnit.SECONDS)
//                .pollingEvery(1, TimeUnit.SECONDS)
//                .ignoring(NoSuchElementException.class);

        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement responseBody = driver.findElement(By.tagName("body"));
//                System.out.println("[body] " + responseBody.getText());
                return responseBody.getText().contains(expectedResponse);
            }
        });

//        Assert commands will show the actual and expected if it fails
//        Assert.assertEquals(driver.getTitle(), "Show form");

    }

    @After
    public void tearDown() throws Exception {
        // Close the browser
        driver.quit();
    }
}
