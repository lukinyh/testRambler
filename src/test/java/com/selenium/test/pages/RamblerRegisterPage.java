package com.selenium.test.pages;

import com.selenium.test.webtestsbase.BasePage;
import com.selenium.test.webtestsbase.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.assertTrue;

/**
 * Created by XE on 06.07.2017.
 */
public class RamblerRegisterPage extends BasePage {

    private static final String PAGE_URL = "https://id.rambler.ru/account/registration";

    private static final String DOMAIN = "//*[@class='menu-3152310865']/div[text()='%s']";

    @FindBy(id = "login.username")
    private WebElement loginUsernameField;

    @FindBy(xpath = "//*[@name='password.main']")
    private WebElement passwordField;

    @FindBy(id = "firstname")
    private WebElement firstnameField;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div/header/h1")
    private WebElement header;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div/form/section[3]/div/div/div[2]")
    private WebElement mailboxErrorMessage;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div/form/div[1]/section[1]/div/div[1]/div[2]")
    private WebElement passwordMessage;

    @FindBy(xpath = "//*[@name=\"login.domain\"]")
    private WebElement mailboxDomain;

    @FindBy(xpath = "//*[@class='content-3792876061']")
    private WebElement domainList;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div/form/div[1]/section[1]/div/div[1]/div[1]/div/span")
    private WebElement passwordEye;

    public RamblerRegisterPage() {
        super(true);
    }

    @Override
    protected void openPage() {
        getDriver().get(PAGE_URL);
    }

    @Override
    public boolean isPageOpened() {
        return firstnameField.isDisplayed()&&passwordField.isDisplayed()&&loginUsernameField.isDisplayed();
    }

    /**
     * inserts text in mailbox field
     *
     * @param text text for input
     */
    public void insertMailboxString(String text) {
        clearField(loginUsernameField);
        loginUsernameField.sendKeys(text);
    }

    /**
     * insert text in password field
     * @param text text for input
     */
    public void insertPasswordString(String text) {
        clearField(passwordField);
        passwordField.sendKeys(text);
    }

    /**
     * submits mailbox field
     */
    public void submitMailbox() {
        loginUsernameField.submit();
    }

    /**
     * submits password field
     */
    public void submitPassword() {
        passwordField.submit();
    }

    /**
     * removes cursor from mailbox field
     */
    public void removeCursorFromMailboxField() {
        submitMailbox();
        firstnameField.click();
    }

    /**
     * removes cursor from password field
     */
    public void removeCursorFromPasswordField() {
        submitPassword();
        firstnameField.click();
    }

    /**
     * clears field
     */
    private void clearField(WebElement webElement) {
        webElement.clear();
    }


    /**
     * gets mailbox error string
     *
     * @return text from mailbox error
     */
    public String getErrorMailboxStringText() {
        return getErrorStringText(mailboxErrorMessage);
    }

    /**
     * gets password message string
     *
     * @return text from message under password field
     */
    public String getMessagePasswordStringText() {
        return getErrorStringText(passwordMessage);
    }
    /**
     * gets some message under webElement
     *
     * @return text from some message under webElement
     */
    private String getErrorStringText(WebElement webElement) {
        return webElement.getText();
    }

    /**
     * gets password's type value
     *
     * @return value of type for password field: password/text
     */
    public String getPasswordTypeValue() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.driver;
            return js.executeScript("return arguments[0].type", passwordField).toString();
        } catch(Exception e) {
            assertTrue("Password field don't include value", false);
            return null;
        }
    }

    /**
     * opens domain list
     * it works strange
     */
    public void openDomainList() {
        mailboxDomain.click();
    }

    /**
     * checks that domain list is opened
     *
     * @return true if domain list is opened
     */
    public boolean isOpenDomainList() {
        try {
            return domainList.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * gets domain value
     *
     * @return value from domain
     */
    public String getDomainValue() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.driver;
            System.out.println("Element: " + js.executeScript("return arguments[0].value", mailboxDomain).toString());
            return js.executeScript("return arguments[0].value", mailboxDomain).toString();
        } catch(Exception e) {
            assertTrue("Domain hasn't element 'value' or  element mailboxDomain is not found'", false);
            return null;
        }
    }

    /**
     * selects domain
     * it works strange
     *
     * @param text domain for selecting
     */
    public void selectDomain(String text) {
        if (isOpenDomainList()) {
            try {
                System.out.println("Try to find element: " + String.format(DOMAIN, text));
                WebElement domain = WebDriverFactory.getDriver().findElement(By.xpath(String.format(DOMAIN, text)));
                System.out.println("Try to click element: " + domain.toString());
                //mailboxDomain.click();
                System.out.println("Try to click element and get in the box: " + isOpenDomainList());
                //domain.submit();
                Actions clicker = new Actions(getDriver());
                int X = mailboxDomain.getSize().getWidth()/2;
                int Y = mailboxDomain.getSize().getHeight()/2;
                //clicker.clickAndHold(domain).perform();
                clicker.moveToElement(domain, X, Y).clickAndHold().perform();
                System.out.println("Is domain list opened?  " + isOpenDomainList());
            } catch (Exception e) {
                assertTrue("Element with name '" + text + "' was not found", false);
            }
        } else {
            assertTrue("Domain list is not opened", false);
        }

    }

    /**
     * shows and hides password value
     */
    public void showAndHidePassword() {
        passwordEye.click();
    }
}
