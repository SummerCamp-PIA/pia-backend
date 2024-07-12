package com.pia.reservation.FrontEndTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.AssertJUnit.fail;

public class RegisterFormTests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testRegistrationForm() throws InterruptedException {
        driver.get("http://localhost:3000/register");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Fill out the registration form
        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
        firstNameInput.sendKeys("John");

        WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastName")));
        lastNameInput.sendKeys("Doe");

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailInput.sendKeys("johndoe@example.com");

        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='phone']")));
        phoneInput.sendKeys("+1234567890");

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordInput.sendKeys("Password1!");

        // Submit the form
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        // Check for errors or assume success
        try {
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'error')]")));
            fail("Registration failed, an error occurred: " + errorMessage.getText());
        } catch (TimeoutException e) {
            System.out.println("No error message was found, assuming registration was successful.");
        }
        Thread.sleep(2000);

    }

}
