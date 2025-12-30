package com.revature.pw;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class tracingDemo {

    @Test
    public void testBasicDemo(){
        try(Playwright playwright=Playwright.create()){
            Browser browser=playwright.chromium().launch();
            BrowserContext context = browser.newContext();

            context.tracing().start(
                    new Tracing.StartOptions()
                            .setScreenshots(true)
                            .setScreenshots(true)
                            .setSources(true)
            );

            System.out.println("Start tracing");

            Page page = context.newPage();

            page.navigate("https://the-internet.herokuapp.com/login");
            page.locator("#username").fill("tomsmith");
            page.locator("#password").fill("SuperSecretPassword!");

            page.locator("button[type = 'submit']").click();
            page.waitForURL("**/secure");

            context.tracing().stop(
                    new Tracing.StopOptions()
                            .setPath(Paths.get("target/traces/login-trace.zip"))
            );

            context.close();
            browser.close();
        }
    }

    @Test
    public void demoAdvancedTracing() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            BrowserContext context = browser.newContext();

            // Start with title for organization
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true)
                    .setTitle("Login Flow Test")  // Shows in Trace Viewer
            );

            Page page = context.newPage();

            // Test scenario 1
            page.navigate("https://the-internet.herokuapp.com/");

            // Create a checkpoint (chunk) in the trace
            context.tracing().startChunk();

            page.locator("a:has-text('Form Authentication')").click();
            page.locator("#username").fill("tomsmith");
            page.locator("#password").fill("SuperSecretPassword!");
            page.locator("button[type='submit']").click();

            // Save this chunk separately
            context.tracing().stopChunk(new Tracing.StopChunkOptions()
                    .setPath(Paths.get("target/traces/trace-login.zip"))
            );

            // Test scenario 2
            context.tracing().startChunk();

            page.locator("a[href='/logout']").click();
            page.waitForURL("**/login");

            context.tracing().stopChunk(new Tracing.StopChunkOptions()
                    .setPath(Paths.get("target/traces/trace-logout.zip"))
            );

            context.tracing().stop();
            context.close();
            browser.close();
        }
    }
    // mvnd exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace
    // target/traces/login-trace.zip"
    // basic demo path

    //or use the chunk paths
}
