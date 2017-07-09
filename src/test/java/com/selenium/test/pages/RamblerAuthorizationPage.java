package com.selenium.test.pages;

import com.selenium.test.webtestsbase.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.assertTrue;

/**
 * Created by XE on 08.07.2017.
 */
public class RamblerAuthorizationPage extends BasePage {

    private static final String PAGE_URL = "https://mail.rambler.ru/";

    @FindBy(xpath = "//*[@name=\"login\"]")
    private WebElement loginField;

    @FindBy(xpath = "//*[@name=\"password\"]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[1]/div[2]/form/div[3]/div/button")
    private WebElement buttonSubmit;

    public RamblerAuthorizationPage() {
        super(true);
    }

    @Override
    protected void openPage() {
        getDriver().get(PAGE_URL);
    }

    @Override
    public boolean isPageOpened() {
        return loginField.isDisplayed()&& passwordField.isDisplayed();
    }

    /**
     * authorizates with credentials
     * @param login text for login field
     * @param password text for password field
     */
    public void authorizate(String login, String password) {
        if (isPageOpened()){
            insertLogin(login);
            insertPassword(password);
            clickSubmit();
        } else {
            assertTrue("MailTests page is not opened", false);
        }
    }

    /**
     * insert login in the field
     *
     * @param login text for login
     */
    public void insertLogin(String login) {
        clearField(loginField);
        loginField.sendKeys(login);
    }

    /**
     * inserts password in the field
     *
     * @param password text for password
     */
    public void insertPassword(String password) {
        clearField(passwordField);
        passwordField.sendKeys(password);
    }

    /**
     * сlicks button Submit
     */
    public void clickSubmit() {
        buttonSubmit.click();
    }

    /**
     * clears field
     */
    private void clearField(WebElement webElement) {
        webElement.clear();
    }
}
