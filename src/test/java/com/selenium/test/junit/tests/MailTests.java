package com.selenium.test.junit.tests;

import com.selenium.test.pages.RamblerAuthorizationPage;
import com.selenium.test.pages.RamblerMailPage;
import com.selenium.test.webtestsbase.WebDriverFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by XE on 08.07.2017.
 */
public class MailTests {
    RamblerMailPage ramblerMailPage;
    RamblerAuthorizationPage ramblerAuthorizationPage;
    @Before
    public void beforeTest() {
        WebDriverFactory.startBrowser(true);
       // ramblerMailPage = new RamblerMailPage();
    }

    @Test
    public void checkInbox(){
        ramblerAuthorizationPage = new RamblerAuthorizationPage();
        ramblerAuthorizationPage.authorizate("nlukinyh", "Qwerty1");
        ramblerMailPage = new RamblerMailPage();

        assertTrue("New mails not found", ramblerMailPage.hasUserNewMail());


    }

    @After
    public void afterTest() {
        WebDriverFactory.finishBrowser();
    }
}
