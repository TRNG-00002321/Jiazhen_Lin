package com.revature.sel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Alert Demo")
public class alertDemo {
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

    @DisplayName("Alert")
    @Test
    public void alertTest(){
        driver.get(BASE_URL + "/javascript_alerts");
        driver.findElement(By.xpath("//button[@onclick='jsAlert()']")).click();
        Alert alert = driver.switchTo().alert();

        String alertText = alert.getText();
        assertTrue(alertText.contains("Alert"));

        alert.accept();
    }
    @DisplayName("Confirm")
    @Test
    public void confirmTest(){
        driver.get(BASE_URL + "/javascript_alerts");
        driver.findElement(By.xpath("//button[@onclick='jsConfirm()']")).click();
        Alert alert = driver.switchTo().alert();

        String alertText = alert.getText();
        assertTrue(alertText.contains("Confirm"));

        alert.accept();
    }

    @DisplayName("Prompt")
    @Test
    public void promptTest(){
        driver.get(BASE_URL + "/javascript_alerts");
        driver.findElement(By.xpath("//button[@onclick='jsPrompt()']")).click();
        Alert prompt = driver.switchTo().alert();

        prompt.sendKeys("Test");
        prompt.accept();

    }
}
