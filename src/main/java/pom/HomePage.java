package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static base.ThreadUtils.sleep;

public class HomePage extends BasePage{

    public static final String URL = "https://www.sfrbusiness.fr/espace-client/portail/#/";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Selectors
    private By homeSideMenu = By.xpath("//*[@class=\"side-menu\"]//*[text()=\"Home\"]");
    private By viewAndManageInvoicesButton = By.xpath("//button[contains(text(), \"VIEW AND MANAGE INVOICES\")]");


    // Actions
    public void openHomeSideMenu(){
        click(homeSideMenu);
        sleep(2000);
    }

    public void viewAndManageInvoices(){
        click(viewAndManageInvoicesButton);
        sleep(2000);
    }
}
