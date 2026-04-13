package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationSteps {
    // Deklarerar variabler som ska användas i hela klassen
    private WebDriver driver;
    private WebDriverWait wait;
    private static int testNr = 0;

    // @Before körs automatiskt FÖRE varje scenario från feature-fil
    @Before
    public void setup() {
        testNr++;
        System.out.println("TEST SCENARIO: " + testNr);
        System.out.println("**************************************************");

        // Tar bort Chrome-varningar (som den för CDP-versionen)
        // System.setProperty("webdriver.chrome.silentOutput", "true");
        // Stänger av loggar från Selenium (så att det inte blir för mycket text i konsolen)
        //java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.OFF);
    }

    @Given("I prepare the test environment for registration")
    public void i_prepare_the_test_environment_for_registration() {
        System.out.println("Startar ett nytt testscenario...");
    }

    @Given("I am using {string} browser")
    public void i_am_using_browser(String browserName) {
        // Om webbläsaren redan är igång, stäng den först
        if (driver != null) {
            driver.quit();
        }

        System.out.println("Startar webbläsare: [" + browserName + "]");
        // Från private metod
        startaBrowser(browserName);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Teststeg (Given, When, Then)
    @Given("I am on the page: CREATE A SUPPORTER ACCOUNT")
    public void i_am_on_the_registration_page() {
        driver.get("file:///C:/Users/ThinkPad/Downloads/Register%20(4)/Register.html");

        getElement(By.cssSelector(".caption-subject"));
        System.out.println("Sidan har laddats och Member Details är synligt.");
    }

    @When("I select {string} as my date of birth")
    public void i_select_as_my_date_of_birth(String date) {
        WebElement dateOfBirth = getElement(By.id("dp"));
        dateOfBirth.sendKeys(date);

        System.out.println("Skriver in födelsedatum: " + date);
    }

    @And("I fill in first name {string} and last name {string}")
    public void i_fill_in_first_name_and_last_name(String firstName, String lastName) {
        WebElement name = getElement(By.cssSelector("#member_firstname"));
        name.sendKeys(firstName);

        WebElement secondName = getElement(By.name("Surname"));
        secondName.sendKeys(lastName);

        System.out.println("Fyllde i namn och efternamn: " + firstName + " " + lastName);
    }

    @And("I enter email {string} and confirm it as {string}")
    public void i_enter_email_and_confirm_it_as(String email, String confirmEmail) {
        WebElement emailBox = getElement(By.cssSelector("#member_emailaddress"));
        emailBox.sendKeys(email);

        WebElement checkEmail = getElement(By.cssSelector("#member_confirmemailaddress"));
        checkEmail.sendKeys(confirmEmail);

        System.out.println("Fyllde i e-post och bekräftelse: " + email + " " + confirmEmail);
    }

    @And("I fill in first name {string} but leave last name empty")
    public void i_fill_in_first_name_but_leave_last_name_empty(String firstName) {
        WebElement name = getElement(By.cssSelector("#member_firstname"));
        name.sendKeys(firstName);
        // Kör .clear() för att vara säkert på att det är tomt
        WebElement secondName = getElement(By.name("Surname"));
        secondName.clear();

        System.out.println("Fyllde i förnamn: " + firstName + " men lämnade efternamn tomt.");
    }

    @And("I enter password {string} and retype it as {string}")
    public void i_enter_password_and_retype_it_as(String password, String confirmPassword) {
        WebElement pass = getElement(By.cssSelector("#signupunlicenced_password"));
        pass.sendKeys(password);

        WebElement confirmPass = getElement(By.cssSelector("#signupunlicenced_confirmpassword"));
        confirmPass.sendKeys(confirmPassword);

        System.out.println("Fyllde i lösenord och bekräftelse: " + password + " " + confirmPassword);
    }

    @And("I check all required checkboxes for terms and ethics")
    public void iCheckAllRequiredCheckboxesForTermsAndEthics() {
        // 1. Villkor (Terms)
        getElement(By.cssSelector("[for='sign_up_25']")).click();
        // 2. Ålder (18+)
        getElement(By.cssSelector("[for='sign_up_26']")).click();
        // 3. Etik (Ethics)
        getElement(By.cssSelector("[for='fanmembersignup_agreetocodeofethicsandconduct']")).click();

        System.out.println("Markerade alla obligatoriska kryssrutor (Villkor, Ålder och Etik).");
    }

    @And("I check only Age and Ethics checkboxes but NOT Terms")
    public void i_check_only_age_and_ethics_checkboxes_but_not_terms() {
        // Hoppar över den första rutan (Terms)

        getElement(By.cssSelector("[for='sign_up_26']")).click();
        getElement(By.cssSelector("[for='fanmembersignup_agreetocodeofethicsandconduct']")).click();

        System.out.println("Markerade kryssrutor Age och Ethics, men hoppade avsiktligt över Terms.");
    }

    @And("I click on the CONFIRM AND JOIN button")
    public void iClickOnTheCONFIRMANDJOINButton() {
        getElement(By.cssSelector(".btn-big.red")).click();

        System.out.println("Klickade på 'CONFIRM AND JOIN' knappen.");
    }

    @Then("a new account should be created successfully")
    public void a_new_account_should_be_created_successfully() {
        String actual = getElement(By.cssSelector("h2.bold")).getText();

        String expected = "THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND";
        assertEquals(expected, actual);

        System.out.println("Bekräftelsetext hittad: " + actual);
    }

    @Then("I should see the error message {string}")
    public void i_should_see_the_error_message(String expected) {
        String actual = getElement(By.cssSelector("[generated='true']")).getText();
        assertEquals(expected, actual);

        System.out.println("Felmeddelandet stämmer: " + actual);
    }


    // @After körs automatiskt EFTER varje scenario
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void startaBrowser(String browserName) {
        if (browserName == null) {
            browserName = "chrome";
        }

        switch (browserName.toLowerCase().trim()) {
            case "edge":
                driver = new EdgeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
    }

    private WebElement getElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
