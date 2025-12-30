package com.training.capstone.config;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class PlaywrightTestBase {
    static Playwright playwright;
    static Browser browser;

    Page page;
    BrowserContext context;

    @BeforeAll
    public static void globalPlaywrightSetup(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
          new BrowserType.LaunchOptions()
              .setHeadless(true)
        );
    }

    @AfterAll
    public static void globalPlaywrightCleanup(){
        browser.close();
        playwright.close();
    }

    @BeforeEach
    public void beforeEachTest(){
        context = browser.newContext();
        page = browser.newPage();
        page.setDefaultTimeout(5000);
    }

    @AfterEach
    public void afterEachTest(){
        context.close();
    }
}
