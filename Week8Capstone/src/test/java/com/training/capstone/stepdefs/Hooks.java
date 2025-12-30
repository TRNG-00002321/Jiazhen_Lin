package com.training.capstone.stepdefs;

import com.training.capstone.utils.PlaywrightManager;
import io.cucumber.java.*;

/**
 * Cucumber hooks for Playwright lifecycle management.
 */
public class Hooks {

    @BeforeAll
    public static void beforeAll() {
        PlaywrightManager.initialize();
    }

    @AfterAll
    public static void afterAll() {
        PlaywrightManager.shutdown();
    }

    @Before
    public void before(Scenario scenario) throws Exception {
        PlaywrightManager.createPage(scenario.getName());
    }

    @After
    public void after(Scenario scenario) throws Exception {
        boolean failed = scenario.isFailed();

        if (failed) {
            // Capture screenshot on failure
            byte[] screenshot = PlaywrightManager.getPage().screenshot();
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
        }

        PlaywrightManager.closePage(scenario.getName(), failed);
    }
}
