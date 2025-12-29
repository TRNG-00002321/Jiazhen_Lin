package com.revature.pw;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@DisplayName("Playwright Demo")
public class pwDemo {

    @DisplayName("Basic playwright test")
    @Test
    public void basicTest(){
        try(Playwright playwright = Playwright.create()){
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false) //Default is headless, different from selenium
                            .setSlowMo(500) //Slow down for visibility
            );

            //new tab
            Page page=browser.newPage();

            page.navigate("https://playwright.dev");

            String title = page.title();

            System.out.println("Title: "+title);
            System.out.println("URL :: " + page.url());

            page.locator("text = Get Started").click();

            assertThat(page).hasURL(Pattern.compile(".*intro"));

            System.out.println("Navigated to :: " + page.url());
        }
    }
}
