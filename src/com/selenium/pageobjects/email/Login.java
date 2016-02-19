package com.selenium.pageobjects.email;

import com.selenium.pageobjects.Browser;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static org.hamcrest.Matchers.startsWith;

/**
 * Created by Tom on 1/21/2016.
 */
public class Login extends LoadableComponent<Login> {
    String URL = "http://mail.centrum.cz";
    String title = "Centrum.cz mail - p?ihlášení";
    private WebDriver driver;
    @FindBy(id = "ego_user")
    private WebElement username;
    @FindBy(id = "ego_secret")
    private WebElement password;
    @FindBy(id = "ego_submit")
    private WebElement login;

    public Login(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void load() {
        Browser.open(URL);
    }

    //    ensure we are at the proper page
    public void isLoaded() {
//        startsWith is hamcrest matcher
        Assert.assertThat(driver.getTitle(), startsWith("Centrum.cz mail"));
    }

    void setUsername(String name) {
        username.clear();
        username.sendKeys(name);
    }

    void setPassword(String pswd) {
        password.clear();
        password.sendKeys(pswd);
    }

    public void loginAs(String name, String pswd) {
        setUsername(name);
        setPassword(pswd);
        login.submit();
    }
}
