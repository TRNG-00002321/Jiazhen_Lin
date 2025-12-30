package com.training.capstone.stepdefs;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.training.capstone.pages.LoginPage;
import com.training.capstone.utils.PlaywrightManager;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import org.junit.platform.suite.api.Suite;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Suite
public class LoginSteps {

    private LoginPage loginPage;

    private Page getPage() {
        return PlaywrightManager.getPage();
    }

    @BeforeAll
    public static void setUpBaselines() {
        try(Playwright playwright = Playwright.create()){
            Browser browser = playwright.chromium().launch();
            BrowserContext browserContext = browser.newContext(
                    new Browser.NewContextOptions()
                            .setViewportSize(1920, 1080)
            );
            Page page = browserContext.newPage();
            page.navigate("https://the-internet.herokuapp.com/login");
            page.locator("input#username").fill("tomsmith");
            page.locator("input#password").fill("SuperSecretPassword!");
            page.locator("button[type ='submit']").click();

            page.waitForLoadState(LoadState.NETWORKIDLE);

            page.screenshot(
                    new Page.ScreenshotOptions()
                            .setPath(Paths.get("visual-baselines/secure-page.png"))
                            .setFullPage(true)
            );
            page.close();
            browserContext.close();
            browser.close();
        }
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage = new LoginPage(getPage());
        loginPage.navigate();
    }

    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("I enter password {string}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        loginPage.clickLogin();
    }

    @When("I login as {string} with password {string}")
    public void iLoginAsWithPassword(String username, String password) {
        loginPage.login(username, password);
    }

    @When("I click logout")
    public void iClickLogout() {
        getPage().click("a.button");
    }

    @Then("I should be on the secure area")
    public void iShouldBeOnTheSecureArea() {
        assertThat(getPage()).hasURL(java.util.regex.Pattern.compile(".*/secure"));
    }

    @Then("I should see success message {string}")
    public void iShouldSeeSuccessMessage(String message) {
        assertThat(getPage().locator("#flash")).containsText(message);
    }

    @Then("I should see error message {string}")
    public void iShouldSeeErrorMessage(String message) {
        assertThat(getPage().locator("#flash")).containsText(message);
    }

    @Then("I should be back on the login page")
    public void iShouldBeBackOnTheLoginPage() {
        assertThat(getPage()).hasURL(java.util.regex.Pattern.compile(".*/login"));
    }

    @Then("I should see message {string}")
    public void iShouldSeeMessage(String message) {
        assertThat(getPage().locator("#flash")).containsText(message);
    }
}
