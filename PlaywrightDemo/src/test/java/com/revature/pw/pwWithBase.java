package com.revature.pw;


import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class pwWithBase extends Base{

    @Test
    public void testLoginWithValidUser()
    {
        page.navigate("https://the-internet.herokuapp.com/login");

        page.getByLabel("Username").fill("tomsmith");
        page.locator("input#password").fill("SuperSecretPassword!");
        //page.locator("[type='submit']").click(); // option 1

        page.locator("text=' Login'").click();
        //will detect multiple elements for just Login, ' Login' is more specific

        assertThat(page).hasURL(Pattern.compile(".*secure"));
    }
}
