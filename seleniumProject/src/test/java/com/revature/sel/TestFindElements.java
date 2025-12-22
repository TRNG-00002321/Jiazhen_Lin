package com.revature.sel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Finding Elements")
public class TestFindElements {
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

    @DisplayName("Test by ID")
    @Test
    public void testFindElementById() {
        driver.get(BASE_URL + "/login");
        WebElement userName = driver.findElement(By.id("username"));
        WebElement pass = driver.findElement(By.id("password"));
        System.out.println("User Name: " + userName.getText());
        System.out.println("Password: " + pass.getText());

    }

    @DisplayName("Test by Name")
    @Test
    public void testFindElementByName() {
        driver.get(BASE_URL + "/login");
        WebElement userName = driver.findElement(By.name("username"));
        WebElement pass = driver.findElement(By.name("password"));

        System.out.println("User Name: " + userName.getText());
        System.out.println("Password: " + pass.getText());

    }
    @DisplayName("Test by TagName")
    @Test
    public void testFindElementByTagName() {
        driver.get(BASE_URL + "/login");
        List<WebElement> logIns = driver.findElements(By.tagName("body"));
        logIns.forEach(element -> System.out.println(element.getText()));
    }

//    @DisplayName("Test User Attempt Login")
//    @Test
//    public void testUserAttemptLogin() {
//        driver.get(BASE_URL + "/login");
//        WebElement userName = driver.findElement(By.name("username"));
//        WebElement pass = driver.findElement(By.name("password"));
//
//        System.out.println("User Name: " + userName.getText());
//        System.out.println("Password: " + pass.getText());
//    }
    @DisplayName("Test with absolute path")
    @Test
    public void testFindElementByAbsolutePath() {
        driver.get(BASE_URL);
        WebElement title = driver.findElement(By.xpath("/html/body/div[2]/div/h2"));
        System.out.println("Title: " + title.getText());
        assertTrue(title.getText().contains("Examples"));

    }

    @DisplayName("Test interaction with elements")
    @Test
    public void testCompleteLogin() throws NoSuchElementException{
        driver.get(BASE_URL + "/login");
        //find elements
        WebElement userName = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("login"));

        // Verify elements are displayed and enabled
        assertTrue(userName.isDisplayed());
        assertTrue(userName.isEnabled());

        assertTrue(password.isDisplayed());
        assertTrue(password.isEnabled());

        assertTrue(loginButton.isDisplayed());
        assertTrue(loginButton.isEnabled());


        // Clear and enter credentials
        userName.sendKeys("tomsmith");
        password.sendKeys("SuperSecretPassword!");
        loginButton.click();

        WebElement flashText = driver.findElements(By.id("flash-messages")).get(0);

        System.out.println("text:: " + flashText.getAttribute("flash"));
        // Verify success (check for success message or URL)

        assertTrue(flashText.getText().contains("You logged into a secure area!") ||
                driver.getCurrentUrl().contains("secure"));



    }
}
