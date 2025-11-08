package ai.pages;

import ai.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ResultsPage {
    WebDriver driver;

    private By flightPrices = By.xpath("//div[contains(@class,'priceSection')]/p");

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void printCheapestFlights() {
        List<WebElement> prices = driver.findElements(flightPrices);
        if (prices.size() >= 2) {
            System.out.println("Cheapest flight: " + prices.get(0).getText());
            System.out.println("Second cheapest flight: " + prices.get(1).getText());
        } else {
            System.out.println("Less than 2 flights found!");
        }
    }
}
