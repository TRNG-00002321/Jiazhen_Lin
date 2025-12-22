package com.revature.sel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("WaitDemo")
public class waitDemo {
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

    @DisplayName("Without waits")
    @Test
    void without_wait(){
        driver.get(BASE_URL + "/dynamic_loading/1");

        WebElement button =  driver.findElement(By.xpath("//button[normalize-space()='Start']"));
        button.click();

        WebElement result = driver.findElement(By.id("finish"));

        assertEquals("Hello World", result.getText());

    }

    @DisplayName("Test Implicit Wait")
    @Test
    void testImplicitWait(){
        driver.get(BASE_URL + "/dynamic_loading/1");
        driver.findElement(By.xpath("//button[normalize-space()='Start']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//        WebElement result = wait.until(
//                ExpectedConditions.presenceOfElementLocated(By.id("finish"))
//        );
        WebElement result = driver.findElement(By.id("finish"));

        assertTrue(result.getText().contains("Hello"));
    }

    @DisplayName("Explicit Wait")
    @Test
    void testExplicitWait(){
        driver.get(BASE_URL + "/dynamic_loading/1");
        driver.findElement(By.xpath("//button[normalize-space()='Start']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement result = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("finish"))
        );

        assertTrue(result.getText().contains("Hello"));
    }

    @DisplayName("Fluent Waits")
    @Test
    void testFluentWaits(){
        driver.get(BASE_URL + "/dynamic_loading/1");
        driver.findElement(By.xpath("//button[normalize-space()='Start']")).click();

        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .withMessage("Wait for result");

        WebElement result = fluentWait.until(driver->{
            WebElement element = driver.findElement(By.id("finish"));
            return element.isDisplayed()? element:null;
        });
        Assertions.assertNotNull(result);
        assertTrue(result.getText().contains("Hello"));
    }



}
