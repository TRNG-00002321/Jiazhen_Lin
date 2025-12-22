package wednesday;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testDragAndDrop {
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
    @DisplayName("Basic drag and drop")
    void test_drag_and_drop() {
        driver.get(BASE_URL + "/drag_and_drop");
//        List<WebElement> elements = driver.findElements(By.id("columns"));
//        for  (WebElement element : elements) {
//            System.out.println(element.getText());
//        }
//
        WebElement source = driver.findElement(By.id("column-a"));
        //System.out.println("Source : " + source.getText());
        WebElement target =  driver.findElement(By.id("column-b"));
        // Get initial text
        String sourceInitialText = source.getText();
        String targetInitialText = target.getText();

        assertEquals("A", sourceInitialText);
        assertEquals("B", targetInitialText);

        // Perform drag and drop
        actions.dragAndDrop(source, target).perform();

        String letter = driver.findElement(By.id("columns")).getText();

        assertEquals('B', letter.charAt(0));
        assertEquals('A', letter.charAt(2)); //2 bc blank space
        //check to make sure they swapped


        // Note: This site might have issues with standard drag-drop
        // Alternative using JavaScript might be needed
    }

    @Test
    @DisplayName("Drag and drop with click-hold-move-release")
    void testDragAndDropManual() {
        driver.get(BASE_URL + "/drag_and_drop");

        WebElement source = driver.findElement(By.id("column-a"));
        WebElement target = driver.findElement(By.id("column-b"));

        // Manual drag and drop sequence
        actions.clickAndHold(source)
                .moveToElement(target)
                .release()
                .perform();

        String letter = driver.findElement(By.id("columns")).getText();

        assertEquals('B', letter.charAt(0));
        assertEquals('A', letter.charAt(2));
    }

    @Test
    @DisplayName("Drag and drop with offset")
    void testDragAndDropByOffset() {
        driver.get("https://jqueryui.com/draggable/");

        // Switch to iframe containing the draggable
        driver.switchTo().frame(driver.findElement(By.cssSelector(".demo-frame")));

        WebElement draggable = driver.findElement(By.id("draggable"));
        //draggable in demo frame

        // Get initial position
        int initialX = draggable.getLocation().getX();
        int initialY = draggable.getLocation().getY();

        // Drag by offset (100px right, 50px down)
        actions.dragAndDropBy(draggable, 100, 50).perform();

        // Verify position changed
        // left and top relative positions are measured from top left corner
        int newX = draggable.getLocation().getX();
        int newY = draggable.getLocation().getY();

        assertTrue(newX > initialX, "Element should have moved right");
        assertTrue(newY > initialY, "Element should have moved down");

        // Switch back to main content
        driver.switchTo().defaultContent();
    }
}
