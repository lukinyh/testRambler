package com.selenium.test.junit.tests;

import com.selenium.test.junit.rules.ScreenShotOnFailRule;
import com.selenium.test.pages.RamblerRegisterPage;
import com.selenium.test.webtestsbase.WebDriverFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by XE on 06.07.2017.
 */
public class MailboxTests {
    private String errorInvalidLogin = "Недопустимый логин";
    private String errorUsedLogin = "Пользователь уже существует";
    private String errorCountOfSymbols = "Логин должен быть от 3 до 31 символов";

    RamblerRegisterPage ramblerRegisterPage;

    @Rule
    public ScreenShotOnFailRule screenShotOnFailRule = new ScreenShotOnFailRule();

    @Before
    public void beforeTest() {
        WebDriverFactory.startBrowser(true);
        ramblerRegisterPage = new RamblerRegisterPage();
    }

    @Test
    // пустое поле
    public void testBlankField(){
        ramblerRegisterPage.submitMailbox();
        try {
            checkingError(errorInvalidLogin);
        } catch (Exception e) {
            assertTrue("Element 'errorMailbox' is not found, expected: " + errorInvalidLogin, false);
        }
    }

    @Test
    // используемый адрес
    public void testUsedLogin() {

        ramblerRegisterPage.insertMailboxString("test");
        ramblerRegisterPage.removeCursorFromMailboxField();
        try {
            checkingError(errorUsedLogin);
        } catch (Exception e) {
            assertTrue("Element 'errorMailbox' is not found, expected: " + errorUsedLogin, false);
        }
    }

    @Test
    // содержит пробелы вначале
    public void testStartWithSpaces() {

        ramblerRegisterPage.insertMailboxString("   qwerty");
        ramblerRegisterPage.removeCursorFromMailboxField();
        try {
            checkingError(errorInvalidLogin);
        } catch (Exception e) {
            assertTrue("Element 'errorMailbox' is not found, expected: " + errorInvalidLogin, false);
        }
    }

    @Test
    // содержит пробелы в середине
    public void testTwoWords() {

        ramblerRegisterPage.insertMailboxString("qwerty qwerty");
        ramblerRegisterPage.removeCursorFromMailboxField();
        try {
            checkingError(errorInvalidLogin);
        } catch (Exception e) {
            assertTrue("Element 'errorMailbox' is not found, expected: " + errorInvalidLogin, false);
        }
    }

    @Test
    // содержит спецсимволы
    public void testSpecialSymbols() {
        String[] specSymbols = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "=", "+", "?", ":", ";", "'", ",", ".", "/", "\"", "ф", "£"};
        int lengthMass;
        lengthMass = specSymbols.length;
        for (int i = 0; i < lengthMass; i = i + 1){
            ramblerRegisterPage.insertMailboxString("natalies".concat(specSymbols[i]));
            ramblerRegisterPage.removeCursorFromMailboxField();
            try {
                checkingError(errorInvalidLogin);
            } catch (Exception e) {
                assertTrue("Element 'errorMailbox' is not found, expected: " + errorInvalidLogin, false);
            }
        }
    }

    @Test
    // содержит точку внутри логина
    public void testDotInside() {
        ramblerRegisterPage.insertMailboxString("nata.lies");
        ramblerRegisterPage.removeCursorFromMailboxField();
        checkingExistingError();
    }

    @Test
    // содержит дефис внутри логина
    public void testMinusInside() {
        ramblerRegisterPage.insertMailboxString("nata-lies");
        ramblerRegisterPage.removeCursorFromMailboxField();
        checkingExistingError();
    }

    @Test
    // содержит подчекивание внутри логина
    public void testUnderlineInside() {
        ramblerRegisterPage.insertMailboxString("nata_lies");
        ramblerRegisterPage.removeCursorFromMailboxField();
        checkingExistingError();
    }


    @Test
    // содержит два спецсимвола подряд внутри логина
    public void testDoubleSpecSymbols() {String[] specSymbols = {"..", "--", "__"};
        int lengthMass;
        lengthMass = specSymbols.length;
        for (int i = 0; i < lengthMass; i = i + 1){
            ramblerRegisterPage.insertMailboxString(String.format("nata%slies", specSymbols[i]));
            ramblerRegisterPage.removeCursorFromMailboxField();
            try {
                checkingError(errorInvalidLogin);
            } catch (Exception e) {
                assertTrue("Element 'errorMailbox' is not found, expected: " + errorInvalidLogin, false);
            }
        }
    }

    @Test
    // содержит число в логине
    public void testNumbersInside() {
        ramblerRegisterPage.insertMailboxString("4natalies4");
        ramblerRegisterPage.removeCursorFromMailboxField();
        checkingExistingError();
    }

    @Test
    // тестовый случай, который вроде бы правильный, но нет
    public void testShouldBeCorrect() {
        ramblerRegisterPage.insertMailboxString("testtesttesttesttest0");
        ramblerRegisterPage.removeCursorFromMailboxField();
        checkingExistingError();
    }


    @Test
    // слишком много символов
    public void testTooManySymbols() {
        ramblerRegisterPage.insertMailboxString("01234567890123456789012345678901234");
        ramblerRegisterPage.removeCursorFromMailboxField();
        try {
            checkingError(errorCountOfSymbols);
        } catch (Exception e) {
            assertTrue("Element 'errorMailbox' is not found, expected: " + errorCountOfSymbols, false);
        }
    }

    @Test
    // слишком мало символов
    public void testNotEnoughSymbols() {
        // не уверена, что такого пользователя у них нет, но считаю, что ошибка должна быть сначала по количеству символов:
        ramblerRegisterPage.insertMailboxString("ff");
        ramblerRegisterPage.removeCursorFromMailboxField();
        try {
            checkingError(errorCountOfSymbols);
        } catch (Exception e) {
            assertTrue("Element 'errorMailbox' is not found, expected: " + errorCountOfSymbols, false);
        }
    }


    // выбираем domain
    //TODO: test is not working yet. Test can't select another element. Needs in re-cording.
    public void testSelectDomain() {
        ramblerRegisterPage.getDomainValue();
        String[] domain_massiv = {"rambler.ru", "lenta.ru", "autorambler.ru", "myrambler.ru", "ro.ru", "rambler.ua"};
        int length = domain_massiv.length;
        for (int i = 0; i < length; i++) {
            System.out.println("i: " + i);
            ramblerRegisterPage.openDomainList();
            System.out.println("isOpenDomainList: " + ramblerRegisterPage.isOpenDomainList());
            ramblerRegisterPage.selectDomain(domain_massiv[i]);
            if (!ramblerRegisterPage.getDomainValue().equals("@" + domain_massiv[i]))
            assertTrue("Domain " + ramblerRegisterPage.getDomainValue() + " is not as expected: " + domain_massiv[i], false);
        }
        assertTrue(true);
    }

    public void checkingError(String errorMessage) {
            String errorText = ramblerRegisterPage.getErrorMailboxStringText();
            assertTrue("Error message under mailbox field (" + errorText + ") is not compare with expected string (" + errorMessage + ")",
                    errorText.equals(errorMessage));
    }

    public void checkingExistingError() {
        try {
            String errorText = ramblerRegisterPage.getErrorMailboxStringText();
            assertTrue("Found error message under mailbox field: " + errorText, false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @After
    public void afterTest() {
        WebDriverFactory.finishBrowser();
    }
}

