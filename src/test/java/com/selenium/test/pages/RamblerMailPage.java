package com.selenium.test.pages;

import com.selenium.test.webtestsbase.BasePage;
import com.selenium.test.webtestsbase.WebDriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.assertTrue;

/**
 * Created by XE on 08.07.2017.
 */
public class RamblerMailPage extends BasePage {
    private static final String PAGE_URL = "https://mail.rambler.ru/";

    @FindBy(xpath = "//*[@class=\"rambler-topline__user\"]")
    private WebElement toplineUser;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[2]/div[2]/div[1]/div[1]/div")
    private WebElement menu;

    @FindBy(xpath = "//*[@class='style-root_1bj style-current_m4I']")
    private WebElement inbox;

    @FindBy(xpath = "//*[@class='bwvh Header-button_3GK']")
    private WebElement btnWriteMail;

    public RamblerMailPage() {
        super();
    }


    @Override
    public void openPage() {
        getDriver().get(PAGE_URL);
    }

    @Override
    public boolean isPageOpened() {
        try {
            boolean l = toplineUser.isDisplayed();
            boolean r = menu.isDisplayed();
            return l&&r; //toplineUser.isDisplayed() && menu.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets attributes from some element
     *
     * @param element object
     * @param attribute which you want to know
     * @return value of element attribute
     */
    public String getAttributeFromElement(WebElement element, String attribute) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.driver;
            return js.executeScript("return arguments[0]."+attribute, element).toString();
        } catch(Exception e) {
            assertTrue(element.toString() +"  field don't include " + attribute, false);
            return null;
        }
    }

    /**
     * checks that text near "Входящие" includes "не прочитано"
     *
     * @return true if "не прочитано" is found.
     */
    public boolean hasUserNewMail () {
        String title = getAttributeFromElement(inbox, "title");
        final String newMail = "не прочитано";
        return title.matches(".*\\b" + newMail + "\\b.*");
    }
}
