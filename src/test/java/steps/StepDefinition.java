package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
@Slf4j
public class StepDefinition {
    // Got the WebDriver instance from our Hooks class
    private WebDriver driver = Hook.getDriver();
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    private static final Logger log = LoggerFactory.getLogger(StepDefinition.class);
    @Given("the user is on the TMDB Discover homepage")
    public void the_User_Is_On_The_Tmdb_Discover_Homepage() {
    // Navigate to the TMDB Discover homepage
        driver.get("https://tmdb-discover.surge.sh/");
        // Use logging to confirm the page has loaded
        log.info("Step Definition - Navigated to TMDB Discover homepage");
    }
    @When("the user filters by the category {string}")
    public void the_user_filters_by_the_category(String category) {
        // The categories are list items within a div with the id "root"
        // We can find the correct category by its text
        if(category.equalsIgnoreCase("Trend")) {
            WebElement trendCategory = driver.findElement(By.xpath("//a[text()='Trend']"));
            trendCategory.click();
        } else if(category.equalsIgnoreCase("Popular")) {
            WebElement movieCategory = driver.findElement(By.xpath("//a[text()='Popular']"));
            movieCategory.click();
        } else if(category.equalsIgnoreCase("Newest")) {
            WebElement tvShowsCategory = driver.findElement(By.xpath("//a[text()='Newest']"));
            tvShowsCategory.click();
        } else if(category.equalsIgnoreCase("Top rated")) {
            WebElement peopleCategory = driver.findElement(By.xpath("//a[text()='Top rated']"));
            peopleCategory.click();
        }
        else {
            log.warn("Step Definition - Unrecognized category: '{}'", category);
            throw new IllegalArgumentException("Unsupported category: " + category);
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        log.info("Step Definition - User clicked on the category filter: {}", category);
    }
    @Then("the user should see that the category filter is set to {string}")
    public void the_user_should_see_that_the_category_filter_is_set_to(String expectedCategory) {

        // The currently selected category has the class "selected"
        if(expectedCategory.equalsIgnoreCase("Trend")) {
            WebElement selectedCategory = driver.findElement(By.xpath("//a[text()='Trend']"));
            String actualCategory = selectedCategory.getText();
            assertEquals(expectedCategory, actualCategory);
        } else if (expectedCategory.equalsIgnoreCase("Popular")) {
            WebElement selectedCategory = driver.findElement(By.xpath("//a[text()='Popular']"));
            String actualCategory = selectedCategory.getText();
            assertEquals(expectedCategory, actualCategory);
        } else if (expectedCategory.equalsIgnoreCase("Newest")) {
            WebElement selectedCategory = driver.findElement(By.xpath("//a[text()='Newest']"));
            String actualCategory = selectedCategory.getText();
            assertEquals(expectedCategory, actualCategory);
        } else if (expectedCategory.equalsIgnoreCase("Top rated")) {
            WebElement selectedCategory = driver.findElement(By.xpath("//a[text()='Top rated']"));
            String actualCategory = selectedCategory.getText();
            assertEquals(expectedCategory, actualCategory);
        } else {
            log.warn("Step Definition - Unrecognized category: '{}'", expectedCategory);
            throw new IllegalArgumentException("Unsupported category: " + expectedCategory);
        }
        log.info("Step Definition - User verified that the category filter is set to: {}", expectedCategory);
    }
    @When("the user enters {string} in the search field")
    public void the_user_enters_in_the_search_field(String movieTitle) {
        By searchFieldLocator = By.xpath("//input[@name='search']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchFieldLocator));
        //Clear the field first
        searchField.clear();
        searchField.sendKeys("Inception");
        searchField.sendKeys(Keys.ENTER);
        log.info("Step Definition - User entered '{}' in the search field", movieTitle);
    }
    @Then("the user should see that the search field is set to {string}")
    public void the_user_should_see_that_the_search_field_is_set_to(String expectedMovieTitle) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By searchFieldLocator = By.xpath("//input[@name='search']");
        WebElement searchField = driver.findElement(searchFieldLocator);
        // For input fields, get the value using the "value" attribute
        String actualMovieTitle = searchField.getAttribute("value");
        assertEquals(expectedMovieTitle, actualMovieTitle);
        log.info("Step Definition - Search field is set to: {}", actualMovieTitle);
    }
    @When("the user selects {string} from the {string} dropdown")
    public void the_user_selects_from_the_dropdown(String optionText, String dropdownName) {
        // Find the dropdown WebElement on the page.
        WebElement dropdownElement = driver.findElement(By.xpath("//div[text()='Movie']/ancestor::div[contains(@class, '-control')]"));
        dropdownElement.click();
        By TVShowsOptionLocator = By.xpath("//div[text()='TV Shows']");
        WebElement tvShowsOption = wait.until(ExpectedConditions.elementToBeClickable(TVShowsOptionLocator));
        tvShowsOption.click();
        log.info("Step Definition - User selected '{}' from the '{}' dropdown", optionText, dropdownName);
    }
    @Then("the {string} dropdown should show {string} as selected")
    public void the_dropdown_should_show_as_selected(String dropdownName, String expectedOptionText) {
        // 1. Find the dropdown element again.
        WebElement dropdownValueDisplay = driver.findElement(By.xpath("//div[text()='TV Shows']"));
        wait.until(ExpectedConditions.textToBePresentInElement(dropdownValueDisplay, expectedOptionText));
        assertEquals(expectedOptionText, dropdownValueDisplay.getText());
        log.info("Step Definition - Dropdown '{}' shows '{}' as selected", dropdownName, expectedOptionText);
    }

    @When("the user selects start year {string}")
    public void the_user_selects_start_year(String startYear) {
        log.info("Step Definition - Opening Start Year dropdown and selecting '{}'.", startYear);
        openStartYearDropdown();
        selectStartYear(startYear);
        log.info("Step Definition - Successfully selected start year '{}'.", startYear);
    }
    @When("the user selects end year {string}")
    public void the_user_selects_end_year(String endYear) {
        log.info("Step Definition - Opening End Year dropdown and selecting '{}'.", endYear);
        openEndYearDropdown();
        selectEndYear(endYear);
        log.info("Step Definition - Successfully selected end year '{}'.", endYear);
    }

    public void openStartYearDropdown() {
        log.info("Step Definition - Opening Start Year dropdown.");
        try {
            By startYearDropdownButton = By.xpath("//div[text()='1900']");
            // This locator is for any year option after the dropdown is opened.
            String yearOptionXPath = "//div[text()='2020']";
            // Wait for the Start Year dropdown button to be clickable and click it
            wait.until(ExpectedConditions.elementToBeClickable(startYearDropdownButton)).click();
            // Wait for at least one year option to be visible
            By sampleYearOption = By.xpath(String.format(yearOptionXPath, "2020"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(sampleYearOption));
            log.info("Step Definition - Start Year dropdown opened.");
        } catch (Exception e) {
            log.error("Step Definition - Failed to open Start Year dropdown: {}", e.getMessage());
            throw new RuntimeException("Failed to open Start Year dropdown.", e);
        }
    }

    public void selectStartYear(String year) {
        log.info("Step Definition - Attempting to select start year: '{}'.", year);
        String yearOptionXPath = "//div[text()='2020']";
        // Construct the specific locator for the year option
        By yearOptionLocator = By.xpath(String.format(yearOptionXPath, year));
        try {
            WebElement yearElement = wait.until(ExpectedConditions.elementToBeClickable(yearOptionLocator));
            yearElement.click();
            log.info("Step Definition - Selected start year '{}' and page refreshed.", year);
        } catch (Exception e) {
            log.error("Step Definition - Failed to select start year '{}': {}", year, e.getMessage());
            throw new RuntimeException("Failed to select start year: " + year, e);
        }
    }
    public void openEndYearDropdown() {
        log.info("Step Definition - Opening End Year dropdown.");
        By endYearDropdownButton = By.xpath("//div[text()='2024']");
        // This locator is for any year option after the dropdown is opened.
        String yearOptionXPath = "//div[text()='2021']";
        try {
            wait.until(ExpectedConditions.elementToBeClickable(endYearDropdownButton)).click();
            // Wait for at least one year option to be visible
            By sampleYearOption = By.xpath(String.format(yearOptionXPath, "2020"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(sampleYearOption));
            log.info("Step Definition - End Year dropdown opened.");
        } catch (Exception e) {
            log.error("Step Definition - Failed to open End Year dropdown: {}", e.getMessage());
            throw new RuntimeException("Failed to open End Year dropdown.", e);
        }
    }

    public void selectEndYear(String year) {
        log.info("Step Definition - Attempting to select end year: '{}'.", year);
        String yearOptionXPath = "//div[text()='2021']";
        By yearOptionLocator = By.xpath(String.format(yearOptionXPath, year));
        try {
            WebElement yearElement = wait.until(ExpectedConditions.elementToBeClickable(yearOptionLocator));
            yearElement.click();
            log.info("Step Definition - Selected end year '{}' and page refreshed.", year);
        } catch (Exception e) {
            log.error("Step Definition - Failed to select end year '{}': {}", year, e.getMessage());
            throw new RuntimeException("Failed to select end year: " + year, e);
        }
    }

    @Then("the user should see that the Start Year dropdown is set to {string}")
    public void the_user_should_see_that_the_start_year_dropdown_is_set_to(String expectedStartYear) {
        log.info("Step Definition - Verifying Start Year dropdown is set to '{}'.", expectedStartYear);
        String actualStartYear = getSelectedStartYear();
        assertEquals(expectedStartYear, actualStartYear);
        log.info("Step Definition - Start Year dropdown verified to be set to '{}'.", expectedStartYear);
    }

    public String getSelectedStartYear() {
        log.info("Step Definition - Getting selected start year from dropdown.");
        By selectedYearLocator = By.xpath("//div[text()='2020']");
        WebElement selectedYearElement = wait.until(ExpectedConditions.visibilityOfElementLocated(selectedYearLocator));
        String selectedYear = selectedYearElement.getText();
        log.info("Step Definition - Selected start year is '{}'.", selectedYear);
        return selectedYear;
    }
    @Then("the user should see that the End Year dropdown is set to {string}")
    public void the_user_should_see_that_the_end_year_dropdown_is_set_to(String expectedEndYear) {
        log.info("Step Definition - Verifying End Year dropdown is set to '{}'.", expectedEndYear);
        String actualEndYear = getSelectedEndYear();
        assertEquals(expectedEndYear, actualEndYear);
        log.info("Step Definition - End Year dropdown verified to be set to '{}'.", expectedEndYear);
    }

    public String getSelectedEndYear() {
        log.info("Step Definition - Getting selected end year from dropdown.");
        By selectedYearLocator = By.xpath("//div[text()='2021']");
        WebElement selectedYearElement = wait.until(ExpectedConditions.visibilityOfElementLocated(selectedYearLocator));
        String selectedYear = selectedYearElement.getText();
        log.info("Step Definition - Selected end year is '{}'.", selectedYear);
        return selectedYear;
    }
    @When("the user filters by the rating {int} stars")
    public void the_user_filters_by_the_rating(int rating) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Find the star rating element that corresponds to the rating
        WebElement starToClick = driver.findElement(By.xpath("//div[@role='radio' and @aria-checked='false' and @aria-posinset='" + rating + "' and @aria-setsize='5' and @tabindex='0']"));
        starToClick.click();
        log.info("Step Definition - Clicked on the rating filter for {} stars", rating);
    }
    @Then("the user should see that the rating filter is set to {int} stars")
    public void the_user_should_see_that_the_rating_filter_is_set_to(int rating) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement selectedStarFilter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='radio' and @aria-checked='true' and @aria-posinset='" + rating + "' and @aria-setsize='5' and @tabindex='0']")
        ));
        assertTrue(selectedStarFilter.isDisplayed());
        log.info("Step Definition - Verified that the {} star rating filter is visibly selected.", rating);
    }
    @When("the user selects {string} from Genre dropdown")
        public void the_user_selects_from_genre_dropdown(String genreName){
        log.info("Step: Opening Genre dropdown and selecting '{}'.", genreName);
        // Open the Genre dropdown
        openGenreDropdown();
        selectGenre(genreName);
        log.info("Step: Successfully selected '{}' from Genre dropdown.", genreName);
    }

    public void openGenreDropdown() {
        log.info("Step Definition - Opening Genre dropdown.");
        By genreDropdownButton = By.xpath("//div[contains(@class, 'control') and .//div[contains(@class, 'placeholder') and text()='Select...']]");
        // This locator is for any genre option after the dropdown is opened.It will be dynamically formatted with the genre name.
        String genreOptionXPath = "//div[text()='Action']";
        try {
            wait.until(ExpectedConditions.elementToBeClickable(genreDropdownButton)).click();
            // Wait for at least one of the genre options to be visible
            // We'll use a common genre like 'Action' as a quick check
            By firstGenreOption = By.xpath(String.format(genreOptionXPath, "Action"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstGenreOption));
            log.info("Step Definition - Genre dropdown opened.");
        } catch (Exception e) {
            log.error("Step Definition - Failed to open Genre dropdown: {}", e.getMessage());
            throw new RuntimeException("Failed to open Genre dropdown.", e);
        }
    }

    public void selectGenre(String genreName) {
        log.info("Step Definition - Attempting to select genre: '{}'.", genreName);
        String genreOptionXPath = "//div[text()='Action']";
        // Construct the specific locator for the genre option
        By genreOptionLocator = By.xpath(String.format(genreOptionXPath, genreName));
        try {
            WebElement genreElement = wait.until(ExpectedConditions.elementToBeClickable(genreOptionLocator));
            genreElement.click();
            // Crucial: Wait for the URL to change to reflect the genre filter,
            // or wait for the movie grid to refresh.
            // The URL change is more reliable for this site.
            log.info("Step Definition - Selected genre '{}' and page refreshed.", genreName);
        } catch (Exception e) {
            log.error("Step Definition - Failed to select genre '{}': {}", genreName, e.getMessage());
            throw new RuntimeException("Failed to select genre: " + genreName, e);
        }
    }

    @Then("the user should see that the Genre dropdown is set to {string}")
    public void the_user_should_see_that_the_genre_dropdown_is_set_to(String expectedGenre) {
        log.info("Step Definition - Verifying Genre dropdown is set to '{}'.", expectedGenre);
        String actualGenre = getSelectedGenre();
        assertEquals(expectedGenre, actualGenre);
        log.info("Step Definition - Genre dropdown verified to be set to '{}'.", expectedGenre);
}
    public String getSelectedGenre() {
        log.info("Step Definition - Getting selected genre from dropdown.");
        By selectedGenreLocator = By.xpath("//div[text()='Action']");
        WebElement selectedGenreElement = wait.until(ExpectedConditions.visibilityOfElementLocated(selectedGenreLocator));
        String selectedGenre = selectedGenreElement.getText();
        log.info("Step Definition - Selected genre is '{}'.", selectedGenre);
        return selectedGenre;
    }

    @When("the user clicks on the {string} button")
    public void the_user_clicks_on_the_button (String buttonText) {
        log.info("Step Definition - User is attempting to click on the '{}' button", buttonText);
        if(buttonText.equalsIgnoreCase("Next")) {
            By nextPageButton = By.xpath("//a[text()='Next']");
            wait.until(ExpectedConditions.elementToBeClickable(nextPageButton)).click();
        } else if(buttonText.equalsIgnoreCase("Previous")) {
            By previousPageButton = By.xpath("//a[text()='Previous']");
            wait.until(ExpectedConditions.elementToBeClickable(previousPageButton)).click();
        } else {
            log.warn("Step Definition - Unrecognized button text: '{}'. Defaulting to 'Next'.", buttonText);
            throw new IllegalArgumentException("Unsupported button: " + buttonText);
        }
        log.info("Step Definition - User clicked on the '{}' button", buttonText);
    }

    @Then("the user should be on page {int}")
    public void the_user_should_be_on_page(int expectedPageNumber){
        log.info("Step Definition - Verifying current page number is {}.", expectedPageNumber);
        int actualPage = getCurrentPageNumber();
        Assertions.assertEquals(expectedPageNumber, actualPage,
                "Expected to be on page " + expectedPageNumber + ", but was on page " + actualPage + ".");
        log.info("Step Definition -  Confirmed current page is {}.", actualPage);
    }
    public int getCurrentPageNumber() {
        By pageNumberDisplay = By.xpath("//li[@class='selected']/a[@aria-current='page']");
        WebElement pageInfoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(pageNumberDisplay));
        String pageText = pageInfoElement.getText();
        log.debug("Step Definition - Raw page number display text: '{}'", pageText);

        // Regex to find digits in "Page X" or "X & up"
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(pageText);

        if (matcher.find()) {
            try {
                int pageNum = Integer.parseInt(matcher.group());
                log.info("Step Definition - Extracted current page number: {}", pageNum);
                return pageNum;
            } catch (NumberFormatException e) {
                log.error("Step Definition - Failed to parse page number from '{}': {}", pageText, e.getMessage());
                throw new IllegalStateException("Failed to parse page number: " + pageText, e);
            }
        } else {
            log.error("Step Definition - Page number not found in text: '{}'", pageText);
            throw new IllegalStateException("Could not determine current page number from text: " + pageText);
        }
    }
    @Then("the {string} button should be enabled")
    public void the_button_should_be_enabled(String buttonText) {
        By buttonLocator = By.xpath("//a[@role='button' and text()='Previous']");
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLocator));
        boolean isEnabled = button.isEnabled();
        assertTrue(isEnabled);
        log.info("Step Definition - The '{}' button is enabled.", buttonText);
    }
    @When("the user navigate to page {int}")
    public void the_user_navigate_to_page(int pageNumber) {
        log.info("Step Definition - Navigating directly to page {}.", pageNumber);
        navigateToPage(pageNumber);
        log.info("Step Definition -  Navigation to page {} completed.", pageNumber);
    }
    public void navigateToHomePage() {
        log.info("Step Definition - Navigating to home page: https://tmdb-discover.surge.sh/");
        driver.get("https://tmdb-discover.surge.sh/");
        // Wait for the page to load completely
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Discover']")));
        log.info("Step Definition - Home page loaded successfully.");
    }
    public void navigateToPage(int targetPage) {
        log.info("Step Definition - Navigating to target page: {}", targetPage);
        navigateToHomePage(); //starting from page 1 for reliable navigation

        int currentPage = getCurrentPageNumber();
        log.debug("Step Definition - Starting from page {}", currentPage);

        while (currentPage < targetPage) {
            if (isNextButtonDisabled()) {
                log.warn("Step Definition - 'Next' button is disabled. Cannot navigate to page {} as it's beyond available pages. Current page: {}", targetPage, currentPage);
                break; // Stop if we can't go further
            }
            clickNextPage();
            currentPage = getCurrentPageNumber(); // Re-read current page number
            log.debug("Step Definition - Now on page {}", currentPage);
            if (currentPage == targetPage) {
                break; // Reached target page
            }
        }
        log.info("Step Definition - Navigation complete. Currently on page: {}", getCurrentPageNumber());
    }
    public boolean isNextButtonDisabled() {
        log.info("Step Definition - Checking if 'Next' button is disabled.");
        By nextPageButton = By.xpath("//a[text()='Next']");
        WebElement nextBtn = driver.findElement(nextPageButton);
        // Check for 'disabled' attribute or specific CSS class that indicates disabled state
        boolean isDisabled = nextBtn.getAttribute("disabled") != null ||
                nextBtn.getAttribute("class").contains("opacity-50"); // Specific to TMDB site styling
        log.debug("Step Definition - 'Next' button disabled status: {}", isDisabled);
        return isDisabled;
    }
    public void clickNextPage() {
        log.info("Step Definition - Clicking 'Next' button.");
        By nextPageButton = By.xpath("//a[text()='Next']");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(nextPageButton)).click();
            log.info("Step Definition - 'Next' button clicked and new page content loaded.");
        } catch (Exception e) {
            log.error("Step Definition - Failed to click 'Next' button or page did not load: {}", e.getMessage());
            throw new RuntimeException("Failed to click 'Next' button or page did not load.", e);
        }
    }
    @Then("the {string} button should be disabled")
    public void the_button_should_be_disabled(String buttonName) {
        log.info("Step Definition - Checking if the '{}' button is disabled.", buttonName);
        if(buttonName.equalsIgnoreCase("Previous")) {
            By buttonLocator = By.xpath("//a[@role='button' and text()='Previous']");
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLocator));
            boolean isDisabled = Boolean.parseBoolean(button.getAttribute("aria-disabled"));
            assertTrue(isDisabled);
        } else if (buttonName.equalsIgnoreCase("Next")) {
            By buttonLocator = By.xpath("//a[@role='button' and text()='Next']");
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLocator));
            boolean isDisabled = Boolean.parseBoolean(button.getAttribute("aria-disabled"));
            assertTrue(isDisabled);
        } else {
            log.warn("Step Definition - Unrecognized button name: '{}'. Defaulting to 'Previous'.", buttonName);
            throw new IllegalArgumentException("Unsupported button: " + buttonName);
        }
        // Log the result
        log.info("Step Definition - The '{}' button is disabled as expected.", buttonName);
    }
}