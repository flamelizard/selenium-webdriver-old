package com.selenium.pageobjects.email;

import com.selenium.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Tom on 1/21/2016.
 */

/*
Not subclass of loadable component
This is feature present on any page in mailbox
*/
public class Logout {
    private WebDriver driver;
    private String logoutCss = "a.icon-css-wrap.logout-header-link";
    @FindBy(css = "a.icon-css-wrap.logout-header-link")
    private WebElement logoutLink;

    @FindBy(id = "mail-logout")
    private WebElement logoutInfo;

    public Logout(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void logout() {
//        System.out.println(driver.getCurrentUrl());
        Utility util = new Utility(driver);
        String frName = util.getFrameHavingElem(By.cssSelector(logoutCss));
        driver.switchTo().frame(frName);
        logoutLink.click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.textToBePresentInElement(logoutInfo,
                "Byli jste bezpeènì odhlášeni"));
//        System.out.println("[logout] " + logoutInfo.getText());
        driver.switchTo().defaultContent();
    }
}
