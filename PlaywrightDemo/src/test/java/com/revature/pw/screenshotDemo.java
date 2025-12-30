package com.revature.pw;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ScreenshotAnimations;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class screenshotDemo {

    @Test
    public void testScreenshot(){
        try(Playwright pw = Playwright.create()){
            Browser browser = pw.chromium().launch();
            Page page = browser.newPage();

            page.navigate("https://playwright.dev/");

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("target/screenshots/basic.png"))
                    .setFullPage(true)
            );
        }
    }

    @Test
    void elementScreenshot() {
        try(Playwright pw = Playwright.create()){
            Browser browser = pw.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setTimeout(5000)
            );
            Page page = browser.newPage();
            page.navigate("https://the-internet.herokuapp.com/");

            // Screenshot of specific element
            page.locator("#heading").screenshot(new Locator.ScreenshotOptions()
                    .setPath(Paths.get("screenshots/header.png"))
            );

            // Element with options
            page.locator(".product-card").first().screenshot(new Locator.ScreenshotOptions()
                    .setPath(Paths.get("screenshots/product.png"))
                    .setOmitBackground(true)  // Transparent background
                    .setAnimations(ScreenshotAnimations.DISABLED)
            );
        }
    }

    @Test
    public void testVideo(){
        try(Playwright pw = Playwright.create()){
            Browser browser = pw.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(true)
                            .setTimeout(5000)
                            .setSlowMo(500)
            );
            BrowserContext browserContext = browser.newContext(
                    new Browser.NewContextOptions()
                            .setRecordVideoDir(Paths.get("target/videos/"))
                            .setRecordVideoSize(1280, 720)
            );
            Page page = browserContext.newPage();
            System.out.println("Recording Started");

            page.navigate("https://the-internet.herokuapp.com/login");
            page.locator("#username").fill("tomsmith");
            page.locator("#password").fill("SuperSecretPassword!");

            page.locator("button[type = submit]").click();
            browserContext.close();

        }
    }

}
