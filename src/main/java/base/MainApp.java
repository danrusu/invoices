package base;

import org.openqa.selenium.WebDriver;
import pom.HomePage;
import pom.InvoicesPage;
import pom.LoginPage;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getAnonymousLogger;

public class MainApp {

    private static WebDriver driver;

    private static final String SFR_USER_NAME = "PBREGOU";
    private static final String SFR_PASSWORD = "4AK3PUd&189";

    public static void main(String[] args) {
        Logger logger = getAnonymousLogger();

        try {
            driver = Driver.getChromeDriver();
            driver.navigate().to(LoginPage.URL);

            LoginPage loginPage = new LoginPage(driver);

            loginPage.login(SFR_USER_NAME, SFR_PASSWORD);

            HomePage homePage = new HomePage(driver);
            homePage.openHomeSideMenu();
            homePage.viewAndManageInvoices();

            InvoicesPage invoicesPage = new InvoicesPage(driver);
            invoicesPage.downloadAllInvoices();

            logger.log(INFO, "Invoices were downloaded in " + Driver.getDownloadFolder());
        }
        catch (Exception e){
            logger.log(SEVERE,"FAILURE: " + e);
        }
        finally {
            driver.quit();
        }
    }
}
