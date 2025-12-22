package com.revature.sel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class windowDemo {
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

    @DisplayName("test window")
    @Test
    public void windowTest(){
        driver.get(BASE_URL + "/windows");

        String originalWindow = driver.getWindowHandle();

        WebElement windowLink = driver.findElement(By.xpath("//a[normalize-space()='Click Here']"));
        windowLink.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.numberOfWindowsToBe(2)
        );

        Set<String> windowNames = driver.getWindowHandles();
        for (String handle : windowNames) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        assertTrue(driver.getCurrentUrl().contains("/windows/new"));
        driver.close();

        driver.switchTo().window(originalWindow);

        assertSame(BASE_URL + "/windows", driver.getCurrentUrl());

    }

}
