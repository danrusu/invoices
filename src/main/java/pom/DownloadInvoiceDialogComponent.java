package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

class DownloadInvoiceDialogComponent extends BaseComponent {

    private static final By DIALOG_CONTAINER_SELECTOR = By.xpath("//*[@role=\"dialog\"]");

    DownloadInvoiceDialogComponent(WebDriver driver) {
        super(driver, DIALOG_CONTAINER_SELECTOR);
    }

    private By downloadButton = By.xpath("//button[contains(text(),\"Download\")]");
    private By csvRadioButton = By.xpath("//label[contains(@class,\"radio-button\")][contains(text(),\"CSV\")]");
    private By closeIcon = By.cssSelector("a[href=\"#\"][class*=\"close\"]");

    void selectCsvFormat(){
        click(csvRadioButton);
    }

    void download(){
        click(downloadButton);
    }

    void close() { click(closeIcon); }
}
