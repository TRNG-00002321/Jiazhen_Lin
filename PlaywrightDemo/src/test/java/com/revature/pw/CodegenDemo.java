package com.revature.pw;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CodegenDemo {
    //commandline
    //mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="codegen https://the-internet.herokuapp.com/login"

    /*
    my mvn set up is weird and uses mvnd instead of mvn
    mvnd exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="codegen https://the-internet.herokuapp.com/login"

     */

    /*
    import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;

public class Example {
  public static void main(String[] args) {
    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
        .setHeadless(false));
      BrowserContext context = browser.newContext();
      Page page = context.newPage();
      page.navigate("https://the-internet.herokuapp.com/login");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username")).click();
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username")).fill("tomsmith");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).click();
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).press("CapsLock");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).fill("S");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).press("CapsLock");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).fill("Super");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).press("CapsLock");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).fill("SuperS");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).press("CapsLock");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).fill("SuperSecret");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).press("CapsLock");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).fill("SuperSecretP");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).press("CapsLock");
      page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).fill("SuperSecretPassword!");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Login")).click();
      page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Secure Area").setExact(true)).click();
      assertThat(page.locator("#content")).isVisible();
      page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Secure Area").setExact(true)).click();
      assertThat(page.getByText("Secure Area Welcome to the")).isVisible();
    }
  }
}
     */

    @Test
    public void loginWithValidCredentials() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://the-internet.herokuapp.com/login");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username")).fill("tomsmith");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).fill("SuperSecretPassword!");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Login")).click();

            page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Secure Area").setExact(true)).click();
            assertThat(page.locator("#content")).isVisible();

            page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Secure Area").setExact(true)).click();
            assertThat(page.getByText("Secure Area Welcome to the")).isVisible();
        }
    }
}
