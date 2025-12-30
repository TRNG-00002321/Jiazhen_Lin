package com.training.capstone.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigate(){
        page.navigate("https://the-internet.herokuapp.com/login");
    }

    public void enterUsername(String username){
        page.locator("input#username").fill(username);
    }

    public void enterPassword(String password){
        page.locator("input#password").fill(password);
    }

    public void clickLogin(){
        page.locator("[type='submit']").click();
    }

    public void login(String username, String password){
        page.locator("input#username").fill(username);
        page.locator("input#password").fill(password);
        page.locator("[type='submit']").click();
    }

}
