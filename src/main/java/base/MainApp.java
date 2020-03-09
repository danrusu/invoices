package base;

import org.openqa.selenium.WebDriver;
import pom.HomePage;
import pom.InvoicesPage;
import pom.LoginPage;

public class MainApp {

    private static WebDriver driver;

    public static final String SFR_USER_NAME = "*****";
    public static final String SFR_PASSWORD = "*****";

    public static void main(String[] args) {

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

            System.out.println("Invoices were downloaded in " + Driver.getDownloadFolder());
        }
        catch (Exception e){
            System.out.println("FAILURE: " + e);
        }
        finally {
            driver.quit();
        }
    }
}
