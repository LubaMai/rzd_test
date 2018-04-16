import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class RzdTests {
    private WebDriver driver;

    private Properties getProperties() {
        File file = new File("src/dataFile.properties");
        FileInputStream fileInput = null;

        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Properties prop = new Properties();

        try {
            prop.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    @Before
    public void setUp() {
        Properties prop = getProperties();

        String driverPath = prop.getProperty("driverPath");
        String driverName = prop.getProperty("driverName");
        String url = prop.getProperty("url");

        System.setProperty(driverName, driverPath);

        if (driverName.equals("webdriver.gecko.driver")) {
            driver = new FirefoxDriver();
        } else if (driverName.equals("webdriver.chrome.driver")) {
            driver = new ChromeDriver();
        } else if (driverName.equals("webdriver.ie.driver")) {
            driver = new InternetExplorerDriver();
        } else {
            throw new IllegalArgumentException("Browser " + driverName + " not found!");
        }
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
