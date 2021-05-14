package tqs.homework.airquality;

import org.openqa.selenium.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumSteps {
    private int numRequestsSeen;
    private int numMissesSeen;
    private int numHitsSeen;

    private WebDriver webDriver = new FirefoxDriver();

    @Given("The current cache statistics at {string}")
    public void currentCacheStats(String url) {
        webDriver.get(url);
        webDriver.manage().window().maximize();
        numRequestsSeen = Integer.parseInt(webDriver.findElement(By.id("numRequests")).getText());
        numMissesSeen = Integer.parseInt(webDriver.findElement(By.id("numMisses")).getText());
        numHitsSeen = Integer.parseInt(webDriver.findElement(By.id("numHits")).getText());
    }

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        webDriver.get(url);
    }

    @And("I click on {string}")
    public void iClickOn(String text) {
        webDriver.findElement(By.xpath("//*[contains (text(), '" + text + "' )]")).click();
    }

    @And("I search by city {string}")
    public void iSearchByCity(String text) {
        WebElement input = webDriver.findElement(By.xpath("//input[@placeholder='City']"));
        input.clear();
        input.sendKeys(text);
        webDriver.findElement(By.id("city")).click();

        WebDriverWait w = new WebDriverWait(webDriver,10);
        w.until(ExpectedConditions.presenceOfElementLocated (By.id("response")));
    }

    @And("I search by coordinates {double}, {double}")
    public void iSearchByCoords(double lat, double lon) {
        WebElement inputLat = webDriver.findElement(By.xpath("//input[@placeholder='Latitude']"));
        WebElement inputLon = webDriver.findElement(By.xpath("//input[@placeholder='Longitude']"));
        inputLat.clear();
        inputLon.clear();
        inputLat.sendKeys(String.valueOf(lat));
        inputLon.sendKeys(String.valueOf(lon));
        webDriver.findElement(By.id("geo")).click();

        WebDriverWait w = new WebDriverWait(webDriver,10);
        w.until(ExpectedConditions.presenceOfElementLocated (By.id("response")));
    }

    @Then("I should see requests increase by {int}")
    public void iShouldSeeRequestsIncr(int increment) {
        int numRequestsNow = Integer.parseInt(webDriver.findElement(By.id("numRequests")).getText());
        assertEquals(numRequestsSeen + increment, numRequestsNow);
    }

    @Then("I should see misses increase by {int}")
    public void iShouldSeeMissesIncr(int increment) {
        int numMissesNow = Integer.parseInt(webDriver.findElement(By.id("numMisses")).getText());
        assertEquals(numMissesSeen + increment, numMissesNow);
    }

    @Then("I should see hits increase by {int}")
    public void iShouldSeeHitsIncr(int increment) {
        int numHitsNow = Integer.parseInt(webDriver.findElement(By.id("numHits")).getText());
        assertEquals(numHitsSeen + increment, numHitsNow);
    }

    @Then("I should see {string}")
    public void iShouldSee(String text) throws InterruptedException {
        if (!existsElementWithText(text))
            throw new AssertionError("\"" + text + "\" not present on page");
    }

    @Then("I should see {string} or {string}")
    public void iShouldSeeOneOrAnother(String text1, String text2) {
        if (!existsElementWithText(text1) && !existsElementWithText(text2))
            throw new AssertionError(
                    "\"" + text1 + "\" or \"" + text2 + "\" not present on page");
    }

    @Then("I should see {int} cards")
    public void iShouldSeeNCards(int num) {
        assertEquals(webDriver.findElements(By.name("card")).size(), num);
    }

    @After
    public void afterScenario() {
        webDriver.quit();
    }

    private boolean existsElementWithText(String text) {
        try {
            webDriver.findElement(By.xpath("//*[contains (text(), '" + text + "')]"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
}
