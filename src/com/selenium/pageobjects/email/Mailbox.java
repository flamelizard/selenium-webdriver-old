package com.selenium.pageobjects.email;

import com.selenium.Utility;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;

/**
 * Created by Tom on 1/22/2016.
 */
public class Mailbox extends LoadableComponent<Mailbox> {
    private WebDriver driver;
    private By pageIdentity = By.className("logo-brand");

    public Mailbox(WebDriver driver) {
        this.driver = driver;
    }

    public void load() {
//        Nothing to load, only verify page's identity
    }

    public void isLoaded() {
//        content split to frames
        Utility util = new Utility(driver);
        Assert.assertTrue(util.hasElementInFrame(pageIdentity));
//        System.out.println("[loaded]");
    }

    //    nested page object
    public void logOut() {
        Logout out = new Logout(driver);
        out.logout();
    }

}
