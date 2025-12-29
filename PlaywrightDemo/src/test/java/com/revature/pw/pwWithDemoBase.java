package com.revature.pw;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class pwWithDemoBase extends ClassDemoBase{
    @Test
    public void demoWaits(){
        navigateTo("/dynamic_loading/1");
        page.locator("#start button").click();

        String result = page.locator("#finish h4").textContent();

        System.out.println("Result :: " + result);
    }

    @Test
    public void demoLocators(){
        navigateTo("/login");

        Locator byId = page.locator("#username");
        Locator byText = page.locator("text=login");
        Locator byRole = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        //Locator byPlaceholder = page.getByPlaceholder("Password"); //for gray out words in text box
        Locator byLabel = page.getByLabel("password");
    }

    @Test
    public void demoInteractions(){
        navigateTo("/login");

        page.locator("#username").fill("tomsmith");
        page.locator("#password").fill("SuperSecretPassword!");

        page.locator("button[type = submit]").click();

        assertThat(page.locator("#flash")).containsText("secure area");

    }


}
