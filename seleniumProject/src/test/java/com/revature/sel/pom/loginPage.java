package com.revature.sel.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class loginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By username = By.id("username");
    private By password = By.id("password");
    private By login = By.id("login");

    private By flashMessage = By.id("flash");

    public loginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public loginPage enterUsername(String name){
        wait.until(
                ExpectedConditions.visibilityOf(driver.findElement(username))
        );
        driver.findElement(username).clear();
        driver.findElement(username).sendKeys(name);

        return this;
    }

    public loginPage enterPassword(String pass){
        driver.findElement(username).click();
        driver.findElement(password).sendKeys(pass);

        return this;
    }

    public SecurePage clickLogin(){
        driver.findElement(login).click();
        return new SecurePage(driver);
    }

    public loginPage clickLoginExceptionPage(){
        driver.findElement(login).click();
        return this;
    }

    public SecurePage login(String username, String pass){
        return enterUsername(username).enterPassword(pass).clickLogin();
    }

}
