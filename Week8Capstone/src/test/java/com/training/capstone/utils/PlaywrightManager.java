package com.training.capstone.utils;

import com.microsoft.playwright.*;

import java.nio.file.*;

/**
 * Manages Playwright browser lifecycle with tracing support.
 */
public class PlaywrightManager {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;
    private static boolean tracingEnabled = true;

    public static void initialize() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true));
    }

    public static Page createPage(String scenarioName) throws Exception {
        // Create directories
        Files.createDirectories(Paths.get("traces"));
        Files.createDirectories(Paths.get("visual-baselines"));

        // Create context with video recording
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setRecordVideoDir(Paths.get("videos/")));

        // Start tracing
        if (tracingEnabled) {
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
        }

        page = context.newPage();
        return page;
    }

    public static Page getPage() {
        return page;
    }

    public static void closePage(String scenarioName, boolean failed) throws Exception {
        // Stop tracing and save on failure
        if (tracingEnabled) {
            String traceFile = "traces/" + scenarioName.replace(" ", "_") + ".zip";
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(traceFile)));

            if (failed) {
                System.out.println("Trace saved: " + traceFile);
                System.out.println("View with: npx playwright show-trace " + traceFile);
                System.out.println("J custom mvn view: \n" +
                        "mvnd exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI " +
                        "-D exec.args=\"show-trace " + traceFile + "\"");
            }
        }

        context.close();
    }

    public static void shutdown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    public static void setTracingEnabled(boolean enabled) {
        tracingEnabled = enabled;
    }
}
