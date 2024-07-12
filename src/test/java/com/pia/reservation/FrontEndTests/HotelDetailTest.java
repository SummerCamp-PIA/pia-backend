package com.pia.reservation.FrontEndTests;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HotelDetailTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Set up the WebDriver and WebDriverWait
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to the hotel detail page
        driver.get("http://localhost:3000/hotel-detail");
    }

    @AfterEach
    public void tearDown() {
        // Close the WebDriver
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoadHotelData() {
        // Wait for the hotel name to be present
        WebElement hotelName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));

        // Assert hotel name and other data
        assertTrue(hotelName.isDisplayed());
        assertTrue(driver.findElement(By.tagName("img")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Rating:')]")).isDisplayed());

        // Log the data for debugging
        System.out.println("Hotel Name: " + hotelName.getText());
    }

    @Test
    public void testGuestInfoInput() {
        // Wait for the guest inputs to be present
        WebElement guest1Name = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Name'][1]")));

        // Check Guest 1 inputs
        assertTrue(guest1Name.isDisplayed());
        assertTrue(driver.findElement(By.xpath("//input[@placeholder='Surname'][1]")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//input[@placeholder='Date of Birth'][1]")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//input[@placeholder='ID Number'][1]")).isDisplayed());

        // Check Guest 2 inputs
        assertTrue(driver.findElement(By.xpath("//input[@placeholder='Name'][2]")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//input[@placeholder='Surname'][2]")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//input[@placeholder='Date of Birth'][2]")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//input[@placeholder='ID Number'][2]")).isDisplayed());
    }

    @Test
    public void testRoomSelection() {
        // Wait for room selection buttons to be present
        List<WebElement> roomButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//button[contains(text(), '$')]")));

        // Assert each room button is displayed
        for (WebElement button : roomButtons) {
            assertTrue(button.isDisplayed());
            System.out.println("Room Button: " + button.getText());
        }
    }

    @Test
    public void testAccommodationTypeSelection() {
        // Wait for accommodation type radio buttons to be present
        List<WebElement> accommodationTypes = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//input[@name='accommodationType']")));

        // Assert each accommodation type is displayed
        for (WebElement radio : accommodationTypes) {
            assertTrue(radio.isDisplayed());
            System.out.println("Accommodation Type: " + radio.getAttribute("value"));
        }
    }

    @Test
    public void testTotalAmountCalculation() {
        // Select a room
        WebElement roomButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), '$')]")));
        roomButton.click();

        // Select an accommodation type
        WebElement accommodationType = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='accommodationType']")));
        accommodationType.click();

        // Check total amount
        WebElement totalAmount = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//footer/p[contains(text(), 'Total Amount:')]")));
        System.out.println("Total Amount: " + totalAmount.getText());

        assertTrue(totalAmount.getText().contains("$"));
    }

    @Test
    public void testPaymentProcess() {
        // Open the payment modal
        WebElement paymentButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Go to Payment')]")));
        paymentButton.click();

        // Wait for payment modal to be present
        WebElement paymentModal = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("payment-modal")));
        assertTrue(paymentModal.isDisplayed());

        // Fill payment details
        driver.findElement(By.xpath("//input[@placeholder='Full Name of the card owner']")).sendKeys("John Doe");
        driver.findElement(By.xpath("//input[@placeholder='Card Number']")).sendKeys("1234567812345678");
        driver.findElement(By.xpath("//input[@placeholder='Expiration Date']")).sendKeys("12/25");
        driver.findElement(By.xpath("//input[@placeholder='CVC/CVV']")).sendKeys("123");

        // Submit payment
        WebElement submitPaymentButton = driver.findElement(By.xpath("//button[contains(text(), 'Make Payment')]"));
        submitPaymentButton.click();

        // Handle the payment result (success or failure)
        // This might require additional steps based on how the payment response is handled
    }
}
