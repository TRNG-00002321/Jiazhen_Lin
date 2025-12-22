package com.revature.sel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Basic selenium test")
public class setBasics {
    private WebDriver driver;

    @BeforeEach
    public void chromeSetup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
    }
    @AfterEach
    public void closeBrowser(){
        if(driver!=null) {
            driver.quit();
        }
       // driver.close();
        //quit closes all
        //close - closes current window
    }

    @Test
    public void testBasics() throws InterruptedException {

        driver.get("https://www.selenium.dev/");
        Thread.sleep(5000);

        String title = driver.getTitle();
        System.out.println("Title:: " + title);
        assertTrue(title.contains("Selenium"));

    }

    @Test
    public void testDocumentation() throws InterruptedException {
        driver.get("https://www.selenium.dev/documentation/");
        String title = driver.getTitle();
        System.out.println("Title:: " + title);
        assertTrue(title.contains("Documentation"));
    }
}
