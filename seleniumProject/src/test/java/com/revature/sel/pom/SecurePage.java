package com.revature.sel.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SecurePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By flashMessage = By.id("flash");

    public SecurePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getFlashMessage(){
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(flashMessage);
                return driver.findElement(flashMessage).getText();
    }
}
