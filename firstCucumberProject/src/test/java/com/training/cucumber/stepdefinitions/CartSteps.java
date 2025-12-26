package com.training.cucumber.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CartSteps {

    @Given("the user is logged in")
    public void the_user_is_logged_in() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("User is logged in");
    }
    @Given("the product catalog is available")
    public void the_product_catalog_is_available() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Product catalog is available");
    }
    @Given("the user is on a {string} page")
    public void the_user_is_on_a_product_page(String product) {
        // Write code here that turns the phrase above into concrete actions
       System.out.println("User is on a " + product +" page");
    }
    @When("the user adds {int} to cart")
    public void the_user_adds_quantity_to_cart(int quantity) {
        // Write code here that turns the phrase above into concrete actions
        int i = quantity;
        System.out.println("Adding " + quantity);
    }
    @Then("the {string} appears in the cart")
    public void the_product_appears_in_the_cart(String product) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(product+" appears in the cart");
    }
    @Then("cart count increases by {int}")
    public void cart_count_increases_by_quantity(int quantity) {
        // Write code here that turns the phrase above into concrete actions
        int in_cart = 3+quantity;
        System.out.println("Cart count increased by " + in_cart );
        //System.out.println("Cart has " + in_cart+Integer.parseInt(quantity) + " items" );
    }
}
