package com.pia.reservation.FrontEndTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeHotelTypesTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));  // Increase wait time
        driver.get("http://localhost:3000/home"); // Update this to your local server URL
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testNavigationOnCardClick() {
        // Wait until the hotel cards are present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("hotel-card")));

        // Click on the first hotel card
        WebElement firstHotelCard = driver.findElements(By.className("hotel-card-overlay")).get(0);
        firstHotelCard.click();

        // Verify the URL
        String expectedUrl = "http://localhost:3000/hotels?filter=Honeymoon%20Hotels";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after clicking first card: " + currentUrl);
        assertTrue(currentUrl.equals(expectedUrl), "Expected URL to be '" + expectedUrl + "' but was: " + currentUrl);

        // Navigate back to home page
        driver.navigate().back();

        // Wait until the hotel cards are present again
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("hotel-card")));

        // Click on the second hotel card
        WebElement secondHotelCard = driver.findElements(By.className("hotel-card-overlay")).get(1);
        secondHotelCard.click();

        // Verify the URL
        expectedUrl = "http://localhost:3000/hotels?filter=Discounted%20Hotels";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after clicking second card: " + currentUrl);
        assertTrue(currentUrl.equals(expectedUrl), "Expected URL to be '" + expectedUrl + "' but was: " + currentUrl);
    }
}
