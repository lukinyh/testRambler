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
public class PasswordTests {
    private String errorCountOfSymbols = "Пароль должен содержать от 6 до 32 символов";
    private String errorRussianSymbols = "Вы вводите русские буквы";
    // Символ "/" не поддерживается. Можно использовать символы ! @ $ % ^ & * ( ) _ - +
    // not found: Сликшом легкий пароль
    private String messageMiddleComplexity =  "Пароль средней сложности";
    private String messageMaxComplexity =  "Сложный пароль";

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
        ramblerRegisterPage.submitPassword();
        try {
            checkingError(errorCountOfSymbols);
        } catch (Exception e) {
            assertTrue("Element 'errorMailbox' is not found, expected: " + errorCountOfSymbols, false);
        }
    }

    @Test
    // символы должны быть скрыты
    public void testHiddenSymbols(){
        String text = "Qwerty1";
        ramblerRegisterPage.insertPasswordString(text);
        assertTrue("Type of field is not password",  ramblerRegisterPage.getPasswordTypeValue().equals("password"));
    }

    @Test
    // символы должны быть открыты
    public void testShowenSymbols(){
        String text = "Qwerty1";
        ramblerRegisterPage.showAndHidePassword();
        ramblerRegisterPage.insertPasswordString(text);
        assertTrue("Type of field is not text",  ramblerRegisterPage.getPasswordTypeValue().equals("text"));
    }

    @Test
    // русские символы
    public void testRussianSymbols(){
        String text = "Йцукен";
        ramblerRegisterPage.insertPasswordString(text);
        try {
            checkingError(errorRussianSymbols);
        } catch (Exception e) {
            assertTrue("Element 'errorPassword' is not found, expected: " + errorRussianSymbols, false);
        }
    }

    @Test
    // пароль средней степени сложности
    public void testMiddleComplexity(){
        String text = "qwerty1";
        ramblerRegisterPage.insertPasswordString(text);
        try {
            checkingError(messageMiddleComplexity);
        } catch (Exception e) {
            assertTrue("Element 'messagePassword' is not found, expected: " + messageMiddleComplexity, false);
        }
    }

    @Test
    // сложный пароль
    public void testMaxComplexity(){
        String text = "*Qweryu!fjl0";
        ramblerRegisterPage.insertPasswordString(text);
        try {
            checkingError(messageMaxComplexity);
        } catch (Exception e) {
            assertTrue("Element 'messagePassword' is not found, expected: " + messageMaxComplexity, false);
        }
    }

    @Test
    // слишком много символов в пароле
    public void testTooManySymbolsComplexity(){
        String text = "*123456789012345678901234567890123";
        ramblerRegisterPage.insertPasswordString(text);
        ramblerRegisterPage.submitPassword();
        try {
            checkingError(errorCountOfSymbols);
        } catch (Exception e) {
            assertTrue("Element 'messagePassword' is not found, expected: " + errorCountOfSymbols, false);
        }
    }

    @Test
    // неразрешенные спецсимволы
    public void testIncorrectSpecSymbols(){
        String[] specSymbols = {"#", "=", "?", ":", ";", "'", ",", ".", "/", "\"", "£", "♠"};

        int lengthMass;
        lengthMass = specSymbols.length;
        for (int i = 0; i < lengthMass; i = i + 1){
            ramblerRegisterPage.insertPasswordString("natalies".concat(specSymbols[i]));
            ramblerRegisterPage.removeCursorFromPasswordField();
            String error = "Символ \""+specSymbols[i]+"\" не поддерживается. Можно использовать символы ! @ $ % ^ & * ( ) _ - +";
            try {
                checkingError(error);
            } catch (Exception e) {
                assertTrue("Element 'errorMailbox' is not found, expected: " + error, true);
            }
        }
        assertTrue(true);
    }

    @Test
    // разрешенные спецсимволы
    public void testCorrectSpecSymbols(){
        String[] specSymbols = {"!", "@", "$", "%", "^", "&", "*", "(", ")", "-", "_", "+"};
        int lengthMass;
        lengthMass = specSymbols.length;
        for (int i = 0; i < lengthMass; i = i + 1){
            ramblerRegisterPage.insertPasswordString("natalies".concat(specSymbols[i]));
            ramblerRegisterPage.removeCursorFromPasswordField();
            try {
                String error = "Символ \""+specSymbols[i]+"\" не поддерживается. Можно использовать символы ! @ $ % ^ & * ( ) _ - +";
                if (ramblerRegisterPage.getMessagePasswordStringText().equals(error))
                assertTrue("Element 'errorMailbox' is not found, not expected: " + error, false);
            } catch (Exception e) {
                assertTrue("Element 'messagePassword' is not found" , false);
            }
        }
        assertTrue(true);
    }

    public void checkingError(String errorMessage) {
        String errorText = ramblerRegisterPage.getMessagePasswordStringText();
        assertTrue("Error message under password field (" + errorText + ") is not compare with expected string (" + errorMessage + ")",
                errorText.equals(errorMessage));
    }

    @After
    public void afterTest() {
        WebDriverFactory.finishBrowser();
    }
}
