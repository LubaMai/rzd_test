import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

public class RzdTests {
    private WebDriver driver;

    @Before
    public void setUp() {
        String driverPath = "C:\\geckodriver.exe";
        String driverName = "webdriver.gecko.driver";
        String url = "http://pass.rzd.ru/";
        System.setProperty(driverName, driverPath);
        driver = new FirefoxDriver();
        driver.get(url);
    }

    @Test
    public void findFreePlaces() {
        String departureCity = "Москва";
        String arrivalCity = "Тула";
        int day = 18;
        int trainNumber = 119;
        String placeType = "Купе";

        ChoicePage choicePage = PageFactory.initElements(driver, ChoicePage.class);
        choicePage.typeCities(departureCity, arrivalCity);
        choicePage.typeDate(day);
        choicePage.clickBuyTicket();

        TrainsPage trainsPage = PageFactory.initElements(driver, TrainsPage.class);
        trainsPage.waitForTrain();
        trainsPage.openTrain(trainNumber, placeType);
        trainsPage.findFreePlace();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
