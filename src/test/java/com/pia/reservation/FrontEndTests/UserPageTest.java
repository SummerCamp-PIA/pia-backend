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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserPageTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));  // Increase wait time
        driver.manage().window().maximize();  // Maximize the browser window
        driver.get("http://localhost:3000/user-page"); // Update this to your local server URL
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    //TODO yapılacak
    @Test
    public void testLoadUserInfo() {
        // Wait for user info fields to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#root div")));

        // Verify user information
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id*='r1']")));
        WebElement surnameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id*='r3']")));
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id*='r5']")));
        WebElement phoneField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id*='r7']")));

        String nameValue = nameField.getAttribute("value");
        String surnameValue = surnameField.getAttribute("value");
        String emailValue = emailField.getAttribute("value");
        String phoneValue = phoneField.getAttribute("value");

        System.out.println("Name: " + nameValue);
        System.out.println("Surname: " + surnameValue);
        System.out.println("Email: " + emailValue);
        System.out.println("Phone: " + phoneValue);

        assertEquals("John", nameValue);
        assertEquals("Doe", surnameValue);
        assertEquals("john.doe@example.com", emailValue);
        assertEquals("1234567890", phoneValue);
    }


    @Test
    public void testLoadUserReservations() {
        // Wait for reservations to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("MuiList-root")));

        // Verify reservations
        List<WebElement> reservations = driver.findElements(By.className("MuiListItemText-primary"));
        assertEquals(0, reservations.size());


    }

    // Çalışıyor
    @Test
    public void testChangePasswordButton() {
        // Wait for Change Password button to be present and click it
        WebElement changePasswordButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Change Password')]")));
        changePasswordButton.click();

        // Verify Change Password form is displayed
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input#oldPassword")));
        WebElement oldPasswordField = driver.findElement(By.cssSelector("input#oldPassword"));
        WebElement newPasswordField = driver.findElement(By.cssSelector("input#newPassword"));
        WebElement confirmPasswordField = driver.findElement(By.cssSelector("input#confirmPassword"));
        WebElement backButton = driver.findElement(By.xpath("//button[contains(text(), 'Back to Reservations')]"));

        System.out.println("Old Password Field Displayed: " + oldPasswordField.isDisplayed());
        System.out.println("New Password Field Displayed: " + newPasswordField.isDisplayed());
        System.out.println("Confirm Password Field Displayed: " + confirmPasswordField.isDisplayed());
        System.out.println("Back Button Displayed: " + backButton.isDisplayed());

        assertTrue(oldPasswordField.isDisplayed());
        assertTrue(newPasswordField.isDisplayed());
        assertTrue(confirmPasswordField.isDisplayed());
        assertTrue(backButton.isDisplayed());
    }

    //Çalışıyor
    @Test
    public void testBackToReservationsButton() {
        // Wait for Change Password button to be present and click it
        WebElement changePasswordButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Change Password')]")));
        changePasswordButton.click();

        // Wait for Back to Reservations button to be present and click it
        WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Back to Reservations')]")));
        backButton.click();

        // Verify reservations are displayed again
        WebElement reservationList = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("MuiList-root")));
        List<WebElement> reservations = reservationList.findElements(By.className("MuiListItemText-primary"));

        System.out.println("Number of reservations found: " + reservations.size());
        for (WebElement reservation : reservations) {
            System.out.println("Reservation: " + reservation.getText());
        }

        assertEquals(0, reservations.size());
    }
}
