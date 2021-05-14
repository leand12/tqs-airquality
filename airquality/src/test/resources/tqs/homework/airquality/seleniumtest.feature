Feature: Selenium Tests

  Scenario: Test Cache
    Given The current cache statistics at "http://localhost:3000/cache"
    When I click on "AirQuality App"
    And I search by city "Aveiro"
    And I search by city "Porto"
    And I search by city "Aveiro"
    And I click on "Cache Statistics"
    Then I should see requests increase by 3
    And I should see misses increase by 2
    And I should see hits increase by 1
    And I should see 3 cards

  Scenario: Test Search by City
    When I navigate to "http://localhost:3000/app"
    And I search by city "Aveiro"
    Then I should see "Aveiro" or "Not Found"
    And I should see 7 cards

  Scenario: Test Search by Coordinates
    When I navigate to "http://localhost:3000/app"
    And I search by coordinates 25, 55
    Then I should see "(25.000000, 55.000000)"
    And I should see 7 cards

  Scenario: Test Search by Invalid City
    When I navigate to "http://localhost:3000/app"
    And I search by city "c"
    Then I should see "Not Found"
    And I should see 0 cards

  Scenario: Test Search by Invalid Coordinates
    When I navigate to "http://localhost:3000/app"
    And I search by coordinates -91, 181
    Then I should see "Not Found"
    And I should see 0 cards
