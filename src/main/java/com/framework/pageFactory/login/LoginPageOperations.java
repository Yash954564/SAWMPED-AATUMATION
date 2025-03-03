package com.framework.pageFactory.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageOperations extends  LoginPageLocators {
    WebDriver driver;

    @FindBy(id = username)
    WebElement Username;

    @FindBy(id = password)
    WebElement Password;

    @FindBy(id = loginButton)
    WebElement LoginButton;

    public LoginPageOperations(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        Username.click();
    }

}
