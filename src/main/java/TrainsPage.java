import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TrainsPage {
    private WebDriver driver;

    private By trainRowSelector = By.cssSelector("div.route-item__train-info.row");
    private String seatTypeInTrainXpathFormat = "//span[contains(text(),'%s')]/ancestor::div[@class='col-xs-15']/following-sibling::*//div[@class='col-xs-10' and contains(text(),'%s')]";
    private By openCarriageSelector = By.cssSelector("a.btn.btn-main-gray.btn-pRight.j-route-select-btn.route-select-btn.btn-sm");
    private By countOfTopPlacesSelector = By.xpath("(//div[@class='col-xs-12 text-right' and contains(text(),'Верхнее')]/following-sibling::div/b)[1]");
    private By countOfBottomPlacesSelector = By.xpath("(//div[@class='col-xs-12 text-right' and contains(text(),'Нижнее')]/following-sibling::div/b)[1]");

    public TrainsPage(WebDriver driver) {
        this.driver = driver;
    }

    public TrainsPage waitForTrain() {
        new WebDriverWait(driver, 40)
                .until(ExpectedConditions.presenceOfElementLocated(trainRowSelector));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0, 800)", "");
        return this;
    }

    public TrainsPage openTrain(int trainNumber, String placeType) {
        String seatTypeInTrainXpath = String.format(seatTypeInTrainXpathFormat, trainNumber, placeType);
        driver.findElement(By.xpath(seatTypeInTrainXpath)).click();
        WebElement checkCarriage = new WebDriverWait(driver, 15)
                .until(ExpectedConditions.elementToBeClickable(openCarriageSelector));
        checkCarriage.click();
        return this;
    }

    public TrainsPage findFreePlace() {
        int countOfTopFreePlaces = Integer.parseInt(driver.findElement(countOfTopPlacesSelector).getText());
        int countOfBottomFreePlaces = Integer.parseInt(driver.findElement(countOfBottomPlacesSelector).getText());
        System.out.println("Количество верхних свободных мест: " + countOfTopFreePlaces);
        System.out.println("Количество нижних свободных мест: " + countOfBottomFreePlaces);
        return this;
    }
}
