package com.revature.sel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("SelectDemo")
public class SelectDemo {
    private WebDriver driver;
    private final String BASE_URL = "https://the-internet.herokuapp.com";

    @BeforeEach
    public void chromeSetup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
    }
    @AfterEach
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DisplayName("Testing Select List")
    @Test
    void testSelectList(){
        driver.get(BASE_URL + "/dropdown");

        WebElement drops = driver.findElement(By.id("dropdown"));
        Select dropdown = new Select(drops);
        dropdown.selectByVisibleText("Option 1");

        WebElement option = dropdown.getFirstSelectedOption();

        assertEquals("Option 1", option.getText());

    }

    Actions actions;
    @DisplayName("Action API In Action")
    @Test
    public void actionsAPIDemo(){
        driver.get(BASE_URL + "/login");

        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.name("login"));
        actions =  new Actions(driver);
        actions.click(username)
                .sendKeys("tomsmith")
                .sendKeys(Keys.TAB)
                .sendKeys("SuperSecretPassword!")
                .click()
                .perform();
    }

}
