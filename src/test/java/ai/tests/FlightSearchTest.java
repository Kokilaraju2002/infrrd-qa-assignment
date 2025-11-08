package ai.tests;

import ai.core.DriverManager;
import ai.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FlightSearchTest {
    private WebDriver driver;
    private HomePage homePage;

    @BeforeClass
    public void setUp() {
        driver = DriverManager.getDriver();
        driver.get("https://www.makemytrip.com/");
        homePage = new HomePage();
    }

    @Test
    public void testFlightSearch() throws InterruptedException {
        homePage.goToFlights();
        homePage.enterFromAndTo();
        homePage.selectDate();
        homePage.searchFlights();
    }

    @AfterClass
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
