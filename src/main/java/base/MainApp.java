package base;

import org.openqa.selenium.WebDriver;
import pom.HomePage;
import pom.InvoicesPage;
import pom.LoginPage;
import utils.FileUtils;

import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Logger;

import static base.GmailSender.getGmailSender;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getAnonymousLogger;

public class MainApp {
    private static Logger logger = getAnonymousLogger();
    private static WebDriver driver;

    // for safety these can be set from environment, or properties
    private static final String SFR_USER_NAME = "*****";
    private static final String SFR_PASSWORD = "*****";
    private static final String GMAIL_USER_NAME = "qatools.ro@gmail.com";
    private static final String GMAIL_PASSWORD = "*****";

    public static void main(String[] args) {
        try {
            driver = Driver.getChromeDriver();

            downloadInvoices(driver);

            EmailObject email = new EmailObject(
                    "qatools.ro@gmail.com",
                    "danginkgo@yahoo.com",
                    "Invoices " + new Date(),
                    "Invoices are attached.",
                    Paths.get(Driver.getDownloadFolder())
            );

            getGmailSender(GMAIL_USER_NAME, GMAIL_PASSWORD)
                    .sendMail(email);
        }
        catch (Exception e){
            logger.log(SEVERE,"FAILURE: " + e);
        }
        finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private static void downloadInvoices(WebDriver driver) {
        driver.navigate().to(LoginPage.URL);
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(SFR_USER_NAME, SFR_PASSWORD);

        HomePage homePage = new HomePage(driver);
        homePage.openHomeSideMenu();
        homePage.viewAndManageInvoices();

        InvoicesPage invoicesPage = new InvoicesPage(driver);
        invoicesPage.downloadAllInvoices();

        String downloadFolder = Driver.getDownloadFolder();
        String successfulDownloadMessage = "Invoices were downloaded in " + downloadFolder;
        logger.log(INFO, successfulDownloadMessage);
    }
}
