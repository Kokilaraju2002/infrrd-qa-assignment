package ai.pages;

import ai.core.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//span[text()='Flights']")
    private WebElement flightTab;

    @FindBy(xpath = "//label[@for='departure']")
    private WebElement datePicker;

    @FindBy(xpath = "//a[text()='Search']")
    private WebElement searchButton;

    public HomePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
        driver.manage().window().maximize();
    }

    public void goToFlights() throws InterruptedException {
        Thread.sleep(4000);
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

        try {
            WebElement closeButton = driver.findElement(By.cssSelector("span[role='presentation']"));
            if (closeButton.isDisplayed()) {
                closeButton.click();
                System.out.println("Closed login popup successfully.");
                Thread.sleep(1500);
            }
        } catch (Exception e) {
            System.out.println("No login popup appeared.");
        }

        try {
            driver.switchTo().frame("webklipper-publisher-widget-container-notification-frame");
            driver.findElement(By.id("webklipper-publisher-widget-container-notification-close-div")).click();
            driver.switchTo().defaultContent();
            System.out.println("Closed notification frame successfully.");
        } catch (Exception e) {
            System.out.println("No notification frame found.");
        }

        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", flightTab);
            Thread.sleep(1000);
            try {
                flightTab.click();
                System.out.println("Clicked Flights tab normally.");
            } catch (Exception e) {
                System.out.println("Normal click failed, trying JS click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", flightTab);
            }
        } catch (Exception e) {
            System.out.println("Retrying Flights click after overlay...");
            Thread.sleep(3000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", flightTab);
        }
    }

    public void enterFromAndTo() throws InterruptedException {
        System.out.println("Waiting for Flights search form to become ready...");

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fromCity")));
            WebElement fromCityDiv = wait.until(ExpectedConditions.elementToBeClickable(By.id("fromCity")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fromCityDiv);
            System.out.println("Clicked From field container.");

            WebElement fromSearchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[contains(@aria-controls,'react-autowhatever') and @type='text']")));
            fromSearchBox.sendKeys("Chennai");
            Thread.sleep(1000);

            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//ul//p[contains(text(),'Chennai')]"))).click();
            System.out.println("From city selected: Chennai");

            WebElement toCityDiv = wait.until(ExpectedConditions.elementToBeClickable(By.id("toCity")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toCityDiv);
            System.out.println("Clicked To field container.");

            WebElement toSearchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[contains(@aria-controls,'react-autowhatever') and @type='text']")));
            toSearchBox.sendKeys("Delhi");
            Thread.sleep(1000);

            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//ul//p[contains(text(),'Delhi')]"))).click();
            System.out.println("To city selected: Delhi");

        } catch (TimeoutException e) {
            System.out.println("❌ Timeout: Could not locate From/To fields after 30s.");
        } catch (Exception e) {
            System.out.println("❌ Unexpected issue in enterFromAndTo(): " + e.getMessage());
        }
    }

    public void selectDate() throws InterruptedException {
        System.out.println("Selecting departure date...");
        try {
            WebElement datePicker = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//label[@for='departure']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", datePicker);
            System.out.println("Clicked on departure date field.");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='DayPicker-Months']")));
            Thread.sleep(1500);

            // pick 10 days ahead
            LocalDate targetDate = LocalDate.now().plusDays(10);
            List<WebElement> allDates = driver.findElements(
                    By.xpath("//div[contains(@class,'DayPicker-Day') and not(contains(@class,'disabled'))]"));

            if (!allDates.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", allDates.get(5));
                System.out.println("Clicked dynamically on available date cell.");
            } else {
                System.out.println("❌ No clickable date cells found.");
            }

        } catch (TimeoutException e) {
            System.out.println("❌ Timeout: Calendar did not load or date not clickable.");
        } catch (Exception e) {
            System.out.println("❌ Error in selectDate(): " + e.getMessage());
        }
    }

    public void searchFlights() throws InterruptedException {
        try {
            searchButton.click();
            System.out.println("Clicked on Search Flights successfully.");
        } catch (Exception e) {
            System.out.println("Normal click failed, using JS click for Search.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);
        }

        System.out.println("✅ Flight search initiated. Keeping browser open for demo...");
        Thread.sleep(10000);
    }
}
