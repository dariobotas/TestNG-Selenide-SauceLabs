package Interfaces;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.testng.Assert.assertTrue;

public interface IGenericMethods<T> {

    Random random = new Random();
    String allowedSpecialCharacters = " " + "/,.:;|-_?()´'";

    T getInstance();

    default T click(By by) {
        $(by).should(Condition.visible).scrollIntoView(true).click();
        return getInstance();
    }

    default T click(SelenideElement field){
        try{
            $(field).should(Condition.visible).scrollIntoView(true).click();
        } catch (Exception e) {
            e.getMessage();
        }
        return getInstance();
    }

    default T click(WebElement field){
        try{
            $(field).should(Condition.visible).scrollIntoView(true).click();
        } catch (Exception e) {
            e.getMessage();
        }
        return getInstance();
    }

    default T click(SelenideElement field, Condition condition){
        try{
            SelenideElement $ = $(field);
            SelenideElement should = $.should(condition);
            $.scrollIntoView(true);
            $.click();
        } catch (Exception e) {
            e.getMessage();
        }
        return getInstance();
    }


    //WAITERS
    default T waitForInvisible(By by){
        $(by).shouldNot(Condition.visible);
        return getInstance();
    }

    default T waitForInvisible(SelenideElement field){
        $(field).shouldNot(Condition.visible);
        return getInstance();
    }

    default T waitForVisible(By by){
        $(by).should(Condition.visible);
        return getInstance();
    }

    default T waitForVisible(SelenideElement field){
        $(field).should(Condition.visible);
        return getInstance();
    }

    default T waitFor(SelenideElement field, Condition condition){
        $(field).should(condition);
        return getInstance();
    }

    default T sleeper(int time){
        try{
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return getInstance();
    }

    //BOOLEANS
    default T isDisplayed(SelenideElement field) {
        assertTrue($(field).should(Condition.visible).isDisplayed());
        return getInstance();
    }

    default T containsText(SelenideElement field, String txt) {
        assertThat(getText(field), containsString(txt));
        return getInstance();
    }


    //GETS
    default String getText(SelenideElement field){ return $(field).should(Condition.visible).getText(); }
    default String getText(By by){ return $(by).should(Condition.visible).getText(); }

    default String getValue(SelenideElement field) { return $(field).should(Condition.visible).getValue(); }

    default String getTextFromInput(SelenideElement field) { return $(field).should(Condition.visible).getValue(); }

    default ElementsCollection getListElements(By by) { return $$(by); }

    default SelenideElement getSelenideElement(SelenideElement field) { return $(field); }

    default SelenideElement getSelenideElement(By by) { return $(by); }

    default int getSize(By by){ return getListElements(by).size(); }

    default String getCurrentUrl() { return WebDriverRunner.getWebDriver().getCurrentUrl(); }

    ///FAKER DATA
    Faker faker = new Faker();

    default String generateRandomSequenceOfStrings(int numberOfStrings, int totalLength, boolean appendAllowedSpecialCharacters){
        String string = "";
        StringBuilder workingString;
        int numberOfSpaces = numberOfStrings - 1;

        if(appendAllowedSpecialCharacters) {
            string = RandomStringUtils.random(totalLength - numberOfSpaces - allowedSpecialCharacters.length(), true, true);
            workingString = new StringBuilder(string);
            workingString = insertAllowedSpecialCharacters(workingString);
        } else {
            string = RandomStringUtils.random(totalLength - numberOfSpaces, true, true);
            workingString = new StringBuilder(string);
        }

        workingString = splitStringWithSpaces(numberOfStrings, workingString);
        string = workingString.toString();
        return string;
    }

    default StringBuilder splitStringWithSpaces(int numbersOfStrings, StringBuilder workingString){
        for(int i = 0; i < numbersOfStrings; i++){
            int insertIndex = random.nextInt(2, workingString.length() - 1);
            workingString.insert(insertIndex, " ");
        }
        return workingString;
    }

    default StringBuilder insertAllowedSpecialCharacters(StringBuilder workingString){
        for(int i = 0; i < allowedSpecialCharacters.length(); i++){
            int insertIndex = random.nextInt(1, workingString.length());
            workingString.insert(insertIndex, allowedSpecialCharacters.charAt(i));
        }
        return workingString;
    }

    //DATE AND TIME
    default String getCurrentDateAndTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
        return localDateTime.format(dateTimeFormatter);
    }

    default String minutesAdderSubtractor(String initialDate, int numberOfMinutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
        LocalDateTime date = LocalDateTime.parse(initialDate, formatter);

        if(numberOfMinutes > 0){
            date = date.plusMinutes(numberOfMinutes);
        } else if(numberOfMinutes < 0) {
            numberOfMinutes = -numberOfMinutes;
            date = date.minusMinutes(numberOfMinutes);
        }
        return date.format(formatter);
    }
}
