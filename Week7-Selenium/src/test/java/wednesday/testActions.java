package wednesday;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.*;

public class testActions {
    private WebDriver driver;
    private Actions actions;
    private final String BASE_URL = "https://the-internet.herokuapp.com";

    @BeforeEach
    public void chromeSetup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
    }
    @BeforeEach
    void setupActions() {
        actions = new Actions(driver);
    }

    @AfterEach
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    @DisplayName("Mouse hover reveals hidden element")
    void testMouseHover() {
        driver.get("https://the-internet.herokuapp.com/hovers");

        // Find the first figure (avatar)
        WebElement figure = driver.findElements(By.cssSelector(".figure")).get(1);
        //automatically gets the first if findElement only

        // Hover over the figure
        actions.moveToElement(figure).perform();

        // Now the caption should be visible
        WebElement caption = figure.findElement(By.cssSelector(".figcaption"));
        assertTrue(caption.isDisplayed(), "Caption should be visible on hover");

        // Verify caption content
        String captionText = caption.getText();
        assertTrue(captionText.contains("user2"), "Caption should contain user info");
    }
    @Test
    @DisplayName("Hover over multiple elements")
    void testMultipleHovers() {
        driver.get("https://the-internet.herokuapp.com/hovers");

        java.util.List<WebElement> figures = driver.findElements(By.cssSelector(".figure"));

        for (int i = 0; i < figures.size(); i++) {
            WebElement figure = figures.get(i);

            // Hover
            actions.moveToElement(figure).perform();

            // Verify caption visible
            WebElement caption = figure.findElement(By.cssSelector(".figcaption h5"));
            assertTrue(caption.isDisplayed(), "Caption " + i + " should be visible");

            // Move away
            actions.moveToElement(driver.findElement(By.tagName("h3"))).perform();
        }
    }
    @Test
    @DisplayName("Hover and click link")
    void testHoverAndClick() {
        driver.get("https://the-internet.herokuapp.com/hovers");

        WebElement figure = driver.findElement(By.cssSelector(".figure"));

        // Hover to reveal link
        actions.moveToElement(figure).perform();

        // Click the revealed link
        WebElement profileLink = figure.findElement(By.cssSelector(".figcaption a"));
        //<a href>
        profileLink.click();

        // Verify navigation
        assertTrue(driver.getCurrentUrl().contains("/users/"));
    }
}