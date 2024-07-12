package com.pia.reservation.FrontEndTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class DenemeProje {
    @Test
    public void testName() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://google.com");
        Thread.sleep(1000);
        driver.close();
    }

    @Test
    public void locators() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://testautomationpractice.blogspot.com/");
        WebElement emailArea = driver.findElement(By.id("email"));
        emailArea.sendKeys("halukuyumsal@test.com");
        WebElement phoneArea = driver.findElement(By.xpath("//input[@id='phone' and @class='form-control']"));
        phoneArea.sendKeys("555566633322");

        // css selector : div#idValue    div.classValue

        WebElement adressArea = driver.findElement(By.cssSelector("textarea#textarea.form-control"));
        adressArea.sendKeys("Erzincan Merkez");

        Thread.sleep(2500);
        driver.close();
    }

    @Test
    public void dropDownSelector() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://testautomationpractice.blogspot.com/");
        WebElement countriesDropDown = driver.findElement(By.xpath("//select[@id='country']"));
        // Select kullanacaksak WebElementin tagName'i select olmak zorunda.
        Select select = new Select(countriesDropDown);
        select.selectByValue("japan"); //value ile japonyayı seçtik
        Thread.sleep(1500);
        select.selectByVisibleText("United Kingdom"); //Ekranda görülen isim ile seçtik
        Thread.sleep(1500);
        select.selectByIndex(0); //United states'i index ile getirdik
        Thread.sleep(1500);
        driver.close();
    }

    @Test
    public void assertions(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://google.com");
        Assertion assertion = new Assertion(); //Assertion'ı tanımladık
        assertion.assertEquals(driver.getTitle(), "Mynet"); //driver.getTittle() => sayfanın başlığını verir
        driver.close();
    }

    @Test
    public void softAssertion(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://google.com");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(driver.getTitle(), "Mynet", "Test doğrulaması yapılmadı");
        driver.close();
        softAssert.assertAll(); //bunu yazmazsan softassertion yapmaz
    }

}
