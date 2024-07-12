package com.pia.reservation.FrontEndTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeFilterTests {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://localhost:3000/home");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testBasicFields() {
        // Set destination
        WebElement destinationInput = driver.findElement(By.name("destination"));
        destinationInput.sendKeys("New York");
        System.out.println("Destination set to: " + destinationInput.getAttribute("value"));

        // Set check-in and check-out dates using JavaScript
        WebElement checkInInput = driver.findElement(By.name("checkIn"));
        WebElement checkOutInput = driver.findElement(By.name("checkOut"));
        setDateInputValue(checkInInput, "2024-07-15");
        setDateInputValue(checkOutInput, "2024-07-20");

        // Validate the date inputs
        String checkInValue = getDateInputValue(checkInInput);
        String checkOutValue = getDateInputValue(checkOutInput);

        System.out.println("Check-in set to: " + checkInValue);
        System.out.println("Check-out set to: " + checkOutValue);

        // Set number of rooms and adults
        WebElement roomsInput = driver.findElement(By.name("rooms"));
        WebElement adultsInput = driver.findElement(By.name("adults"));
        roomsInput.sendKeys("2");
        adultsInput.sendKeys("4");
        System.out.println("Rooms set to: " + roomsInput.getAttribute("value"));
        System.out.println("Adults set to: " + adultsInput.getAttribute("value"));

        // Set min and max price
        WebElement minPriceInput = driver.findElement(By.name("minPrice"));
        WebElement maxPriceInput = driver.findElement(By.name("maxPrice"));
        minPriceInput.sendKeys("100");
        maxPriceInput.sendKeys("500");
        System.out.println("Min price set to: " + minPriceInput.getAttribute("value"));
        System.out.println("Max price set to: " + maxPriceInput.getAttribute("value"));

        // Validate that the inputs are correctly set
        assertEquals("New York", destinationInput.getAttribute("value"), "Destination input mismatch");
        assertEquals("2024-07-15", checkInValue, "Check-in input mismatch");
        assertEquals("2024-07-20", checkOutValue, "Check-out input mismatch");
        assertEquals("2", roomsInput.getAttribute("value"), "Rooms input mismatch");
        assertEquals("4", adultsInput.getAttribute("value"), "Adults input mismatch");
        assertEquals("100", minPriceInput.getAttribute("value"), "Min price input mismatch");
        assertEquals("500", maxPriceInput.getAttribute("value"), "Max price input mismatch");
    }

    private void setDateInputValue(WebElement input, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value', arguments[1])", input, value);
        System.out.println("Set input value to: " + value);
    }

    private String getDateInputValue(WebElement input) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].value", input);
    }

    @Test
    public void testPriceValidation() {
        // Set min and max price to invalid values
        WebElement minPriceInput = driver.findElement(By.name("minPrice"));
        WebElement maxPriceInput = driver.findElement(By.name("maxPrice"));
        minPriceInput.sendKeys("500");
        maxPriceInput.sendKeys("100");

        // Wait for validation to trigger
        WebElement errorModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("price-error-modal")));
        assertTrue(errorModal.isDisplayed());

        // Print actual error message
        WebElement errorMessageElement = errorModal.findElement(By.className("modal-content")).findElement(By.tagName("p"));
        String actualErrorMessage = errorMessageElement.getText();
        System.out.println("Actual Error Message: " + actualErrorMessage);

        // Assert error message
        String expectedErrorMessage = "Minimum price should be less than or equal to maximum price.";
        assertEquals(expectedErrorMessage, actualErrorMessage);

        // Close modal
        WebElement closeModalButton = errorModal.findElement(By.tagName("button"));
        closeModalButton.click();

        // Ensure modal is closed
        wait.until(ExpectedConditions.invisibilityOf(errorModal));
    }

    //TODO yapılacak
    @Test
    public void testSelectFields() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Open and select accommodation type
        try {
            WebElement accommodationTypePlaceholder = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-33-placeholder")));
            js.executeScript("arguments[0].click();", accommodationTypePlaceholder);
            List<WebElement> accommodationOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".css-1jqq78o-placeholder")));
            for (WebElement option : accommodationOptions) {
                if (option.getText().equals("Hotel")) {
                    js.executeScript("arguments[0].click();", option);
                    break;
                }
            }
            System.out.println("Accommodation type selected.");
        } catch (Exception e) {
            System.err.println("Error selecting accommodation type: " + e.getMessage());
        }

        // Open and select property type
        try {
            WebElement propertyTypePlaceholder = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-34-placeholder")));
            js.executeScript("arguments[0].click();", propertyTypePlaceholder);
            List<WebElement> propertyOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".css-1jqq78o-placeholder")));
            for (WebElement option : propertyOptions) {
                if (option.getText().equals("Residential")) {
                    js.executeScript("arguments[0].click();", option);
                    break;
                }
            }
            System.out.println("Property type selected.");
        } catch (Exception e) {
            System.err.println("Error selecting property type: " + e.getMessage());
        }

        // Open and select amenities
        try {
            WebElement amenitiesPlaceholder = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-35-placeholder")));
            js.executeScript("arguments[0].click();", amenitiesPlaceholder);
            List<WebElement> amenitiesOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".css-1jqq78o-placeholder")));
            for (WebElement option : amenitiesOptions) {
                if (option.getText().equals("WiFi")) {
                    js.executeScript("arguments[0].click();", option);
                    break;
                }
            }
            System.out.println("Amenities selected.");
        } catch (Exception e) {
            System.err.println("Error selecting amenities: " + e.getMessage());
        }

        // Perform search
        try {
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".filter-button-search")));
            js.executeScript("arguments[0].click();", searchButton);
            System.out.println("Search button clicked.");

            // Check for results presence
            List<WebElement> resultsList = driver.findElements(By.id("results"));
            if (!resultsList.isEmpty()) {
                System.out.println("Results found before waiting.");
            } else {
                System.out.println("Results not found before waiting.");
            }

            // Wait for the results container to be visible
            WebElement resultsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("results")));
            assertTrue(resultsContainer.isDisplayed());
            System.out.println("Results are displayed.");
        } catch (Exception e) {
            System.err.println("Error validating results: " + e.getMessage());
        }
    }
}