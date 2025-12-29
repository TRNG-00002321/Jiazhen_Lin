package com.revature.pw;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class Base {
    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    public static void globalPlaywrightSetup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100)
        );

    }

    @AfterAll
    public static void globalPlaywrightCleanup() {
        browser.close();
        playwright.close();
    }

    @BeforeEach
    public void createContextAndPage() {
        context = browser.newContext();
        page = browser.newPage();
        page.setDefaultTimeout(5000);
    }

    @AfterEach
    public void closeContext() {
        context.close();
    }

}
