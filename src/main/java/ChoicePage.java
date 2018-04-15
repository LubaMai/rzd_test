import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChoicePage {
    private WebDriver driver;

    @FindBy(id = "name0")
    private WebElement departureCityField;
    @FindBy(id = "name1")
    private WebElement arrivalCityField;
    @FindBy(xpath = "//div[@class='box-form__datetime__calendar sh_calendar']")
    private WebElement calendarElement;
    @FindBy(id = "Submit")
    private WebElement submitButton;

    private String choiceDayXpathFormat = "(//span[@class='select-time days45 near-time' and text()='%s'])[1]";

    public ChoicePage(WebDriver driver) {
        this.driver = driver;
    }


    public TrainsPage clickBuyTicket() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.elementToBeClickable(By.id("Submit")));
        submitButton.click();
        return new TrainsPage(driver);
    }

    public ChoicePage typeCities(String departureCity, String arrivalCity) {
        departureCityField.sendKeys(departureCity);
        arrivalCityField.sendKeys(arrivalCity);
        return this;
    }

    public ChoicePage typeDate(int day) {
        String choiceDayXpath = String.format(choiceDayXpathFormat, day);
        calendarElement.click();
        driver.findElement(By.xpath(choiceDayXpath)).click();
        return this;
    }
}



