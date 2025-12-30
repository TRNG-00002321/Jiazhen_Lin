package com.training.capstone.stepdefs;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.training.capstone.utils.PlaywrightManager;
import com.training.capstone.utils.VisualTestUtils;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import org.junit.platform.suite.api.Suite;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@Suite
public class VisualSteps {

    private Page getPage() {
        return PlaywrightManager.getPage();
    }

    @BeforeAll
    public static void setBaselines() {
        try(Playwright playwright=Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            BrowserContext browserContext = browser.newContext(
                    new Browser.NewContextOptions()
                            .setViewportSize(1920, 1080)
            );
            Page page = browserContext.newPage();

            page.navigate("https://the-internet.herokuapp.com/login");
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.screenshot(
                    new Page.ScreenshotOptions()
                            .setPath(Paths.get("visual-baselines/login-page.png"))
                            .setFullPage(true)
            );

            Locator login = page.locator("form[name='login']");
            login.screenshot(
                    new Locator.ScreenshotOptions()
                    .setPath(Paths.get("visual-baselines/login-form.png"))
            );

            page.navigate("https://the-internet.herokuapp.com");
            page.waitForLoadState(LoadState.NETWORKIDLE);
            page.screenshot(
                    new Page.ScreenshotOptions()
                            .setPath(Paths.get("visual-baselines/homepage.png"))
                            .setFullPage(true)
            );
        }
    }

    @Given("I navigate to the homepage")
    public void iNavigateToTheHomepage() {
        getPage().navigate("https://the-internet.herokuapp.com/");
    }

    @When("the page has fully loaded")
    public void thePageHasFullyLoaded() {
        getPage().waitForLoadState();
    }

    @Then("the page should visually match {string} baseline")
    public void thePageShouldVisuallyMatchBaseline(String baselineName) throws Exception {
        boolean matches = VisualTestUtils.compareWithBaseline(
                getPage(),
                baselineName,
                new Page.ScreenshotOptions().setFullPage(true)
        );
        assertTrue(matches, "Page should match baseline: " + baselineName);
    }

    @Then("the login form should visually match {string} baseline")
    public void theLoginFormShouldVisuallyMatchBaseline(String baselineName) throws Exception {
        Locator form = getPage().locator("#login");
        byte[] screenshot = form.screenshot();

        // Use element-level comparison
        boolean matches = VisualTestUtils.compareElementWithBaseline(
                screenshot, baselineName
        );
        assertTrue(matches, "Element should match baseline: " + baselineName);
    }
}
