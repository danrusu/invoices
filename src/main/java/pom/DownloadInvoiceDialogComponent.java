package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DownloadInvoiceDialogComponent extends BaseComponent {

    public static final By DIALOG_CONTAINER_SELECTOR = By.xpath("//*[@role=\"dialog\"]");

    public DownloadInvoiceDialogComponent(WebDriver driver) {
        super(driver, DIALOG_CONTAINER_SELECTOR);
    }

    private By downloadButton = By.xpath("//button[contains(text(),\"Download\")]");
    private By pdfRadioButton = By.xpath("//label[contains(@class,\"radio-button\")][contains(text(),\"PDF\")]");
    private By csvRadioButton = By.xpath("//label[contains(@class,\"radio-button\")][contains(text(),\"CSV\")]");
    private By closeIcon = By.cssSelector("a[href=\"#\"][role=\"button\"]");

    public void selectPdfFormat(){
        click(pdfRadioButton);
    }

    public void selectCsvFormat(){
        click(csvRadioButton);
    }

    public void download(){
        click(downloadButton);
    }

    public void close() { click(closeIcon); }
}
