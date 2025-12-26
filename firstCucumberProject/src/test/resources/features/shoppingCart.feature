#As an online shopper
#I want to manage items in my shopping cart
#So that I can purchase the products I need
#
#Acceptance Criteria:
#- User can add items to cart
#- User can view cart contents
#- User can update item quantities
#- User can remove items from cart
#- Cart shows total price
#- Empty cart shows appropriate message

@cart
Feature: Shopping Cart Management
  As an online shopper
  I want to manage items in my shopping cart
  So that I can purchase the products I need

  Background:
    Given the user is logged in
    And the product catalog is available

  @smoke
  Scenario: Add single item to cart
    # TODO: Write the scenario
    # Given: User is on a product page
    Given the user is on a product page
    # When: User clicks add to cart
    When the user clicks add to cart
    # Then: Item appears in cart, cart count updates
    Then the item appears in cart
    And cart count is updated


  Scenario Outline: Add multiple quantities of an item
    # TODO: Write the scenario
    # Consider quantity selector interaction
    Given the user is on a "<Product>" page
    When the user adds <Quantity> to cart
    Then the "<Product>" appears in the cart
    And cart count increases by <Quantity>
    Examples: #need outline to have examples
      | Product | Quantity|
      | a       | 1       |
      | b       | 30      |
      | c       | 2       |
      | d       | 10      |



  Scenario: View cart contents
    # TODO: Write the scenario
    # Include verification of item details shown

  Scenario: Update item quantity in cart
    # TODO: Write the scenario
    # Include before/after quantity and price verification

  Scenario: Remove item from cart
    # TODO: Write the scenario
    # Verify item no longer appears and price updates

  Scenario: Empty cart displays message
    # TODO: Write the scenario
    # Verify appropriate message when cart is empty

  Scenario: Cart total calculates correctly
    Given the user has the following items in cart:
      | Product     | Price  | Quantity |
      | Widget A    | 10.00  | 2        |
      | Widget B    | 25.00  | 1        |
    Then the cart subtotal should be "$45.00"