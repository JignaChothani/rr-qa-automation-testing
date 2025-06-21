Feature: FilteringAndPagination for TMDB Discover Website

  Scenario: Filter to see only for Trending category
    Given the user is on the TMDB Discover homepage
    When the user filters by the category "Trend"
    Then the user should see that the category filter is set to "Trend"

  Scenario: Filter to see only for Popular category
    Given the user is on the TMDB Discover homepage
    When the user filters by the category "Popular"
    Then the user should see that the category filter is set to "Popular"

  Scenario: Filter to see only for Top Rated category
    Given the user is on the TMDB Discover homepage
    When the user filters by the category "Top rated"
    Then the user should see that the category filter is set to "Top rated"

  Scenario: Filter to see only for Newest category
    Given the user is on the TMDB Discover homepage
    When the user filters by the category "Newest"
    Then the user should see that the category filter is set to "Newest"

  Scenario: Filter to see only for Search field
    Given the user is on the TMDB Discover homepage
    When the user enters "Inception" in the search field
    Then the user should see that the search field is set to "Inception"

  Scenario: Filter to see only TV Shows by Type
    Given the user is on the TMDB Discover homepage
    When the user selects "TV Shows" from the "Type" dropdown
    Then the "Type" dropdown should show "TV Shows" as selected

  Scenario: Filter to see only specific Year of Release that are selected
    Given the user is on the TMDB Discover homepage
    When the user selects start year "2020"
    And the user selects end year "2021"
    Then the user should be on page 1
    And the user should see that the Start Year dropdown is set to "2020"
    And the user should see that the End Year dropdown is set to "2021"

  Scenario: Filter to see only specific Rating stars
    Given the user is on the TMDB Discover homepage
    When the user filters by the rating 5 stars
    Then the user should see that the rating filter is set to 5 stars

    Scenario: Filter to see only specific Genres
      Given the user is on the TMDB Discover homepage
      When the user selects "Action" from Genre dropdown
      Then the user should be on page 1
      And the user should see that the Genre dropdown is set to "Action"

  Scenario: Pagination to see next page of results
    Given the user is on the TMDB Discover homepage
    When the user clicks on the "Next" button
    Then the user should be on page 2
    And the "Previous" button should be enabled

  Scenario: Pagination to see previous page of results
    Given the user is on the TMDB Discover homepage
    And the user navigate to page 2
    When the user clicks on the "Previous" button
    Then the user should be on page 1
    And the "Previous" button should be disabled


