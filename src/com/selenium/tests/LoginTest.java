package com.selenium.tests;

import com.selenium.pageobjects.Browser;
import com.selenium.pageobjects.email.Login;
import com.selenium.pageobjects.email.Mailbox;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Tom on 1/21/2016.
 */
public class LoginTest {
    String user = "mymail123@centrum.cz";
    String pswd = "mail321";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "./src/com/resources/chromedriver.exe");
    }

    @Test
    public void testLoginLogout() {
        Browser.useFirefox();
        try {
            Login loginPage = new Login(Browser.driver());
//            .get is an abstract class of LoadableComponent
//           It executes overridden .load and .isLoaded
            loginPage.get();
            loginPage.loginAs(user, pswd);

            Mailbox mailbox = new Mailbox(Browser.driver());
            mailbox.get();
            mailbox.logOut();


        } finally {
            Browser.close();
        }
    }
}
