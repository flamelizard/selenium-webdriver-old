package com.selenium;

/**
 * Created by Tom on 1/8/2016.
 */

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

// First hands-on with Selenium WebDriver
// Very simple and crude structure, learning the ropes
public class SignUpFormTest {

    String url = "file:///D:/git-repos/web_basic/sample_login_page.html";
    String formName = "SignUpForm";
    Map<String, String> userData = new HashMap<>();
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "./src/com/resources/chromedriver.exe");

        userData.put("name", "jack black");
        userData.put("email", "jack@gmail.us");
        userData.put("birth", "1956");

        // Launch Chrome
        driver = new ChromeDriver();
//        driver.manage().window().maximize();
        driver.get(url);
    }

    @Test
    public void testGoogleSearch() {
        WebElement form = driver.findElement(By.name(formName));
//        printElementInfo(element);

//        fill in form
        for (String name : userData.keySet()) {
            WebElement input = driver.findElement(By.name(name));
            input.sendKeys(userData.get(name));
        }
        form.submit();

//        check presence of values filled in the form
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            //            overridden "apply" method
            public Boolean apply(WebDriver d) {
                WebElement body = d.findElement(By.tagName("body"));
                String all_text = body.getText();
//                System.out.println("[body text]" + all_text);
                for (String userInfo : userData.values()) {
                    if (!all_text.contains(userInfo)) {
                        return false;
                    }
                }
                return true;
            }
        });

        Assert.assertEquals("Show form", driver.getTitle());
    }

    public void printElementInfo(By by) {
        WebElement elem = driver.findElement(by);
        System.out.printf("[element info] tag=%s name=%s %n", elem.getTagName(),
                elem.getAttribute("name"));
        System.out.printf("class=%s id=%s text=%s %n", elem.getAttribute
                ("class"), elem.getAttribute("id"), elem.getText());
        System.out.printf("displayed=%s enabled=%s %n", elem.isDisplayed(), elem
                .isEnabled());
    }

    public void printElementInfo(WebElement elem) {
        System.out.printf("[element info] tag=%s name=%s %n", elem.getTagName(),
                elem.getAttribute("name"));
        System.out.printf("class=%s id=%s text=%s %n", elem.getAttribute
                ("class"), elem.getAttribute("id"), elem.getText());
        System.out.printf("displayed=%s enabled=%s %n", elem.isDisplayed(), elem
                .isEnabled());
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @After
    public void tearDown() throws Exception {
        // Close the browser
        driver.quit();
    }
}