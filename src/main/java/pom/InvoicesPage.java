package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.stream.IntStream;

import static utils.ThreadUtils.sleep;
import static java.util.logging.Level.INFO;
import static java.util.logging.Logger.getAnonymousLogger;

public class InvoicesPage extends BasePage {

    private static final long ACTIONS_TIMEOUT = 2000;

    public InvoicesPage(WebDriver driver) {
        super(driver);
    }

    private By invoices = By.cssSelector("xnf-facture-item");

    private void clickInvoiceDownloadLink(int invoiceIndex){
        String invoiceDownloadLinkXpathTemplate =
                "(//xnf-facture-item//a[text()=\"Download\"])[@invoiceIndex]";
        By invoiceDownloadLink = By.xpath(invoiceDownloadLinkXpathTemplate
                .replace("@invoiceIndex", "" + invoiceIndex)
        );
        click(invoiceDownloadLink);
    }

    private void downloadInvoices(int invoiceIndex){
        String downloadInvoiceMessage = "Download invoice #" + invoiceIndex;
        getAnonymousLogger().log(INFO, downloadInvoiceMessage);
        sleep(ACTIONS_TIMEOUT);
        clickInvoiceDownloadLink(invoiceIndex);
        sleep(ACTIONS_TIMEOUT);

        DownloadInvoiceDialogComponent downloadInvoiceDialog = new DownloadInvoiceDialogComponent(driver);

        downloadInvoiceDialog.download(); // PDF
        sleep(ACTIONS_TIMEOUT);

        downloadInvoiceDialog.selectCsvFormat(); // CSV
        downloadInvoiceDialog.download();
        sleep(ACTIONS_TIMEOUT);

        downloadInvoiceDialog.close();
        sleep(ACTIONS_TIMEOUT);
    }

    private int getInvoiceCount() {
        return driver.findElements(invoices).size();
    }

    public void downloadAllInvoices() {
        int invoiceCount = getInvoiceCount();
        String downloadAllInvoicesMessage = invoiceCount + " invoices to download!";
        getAnonymousLogger().log(INFO, downloadAllInvoicesMessage);
        IntStream.rangeClosed(1, invoiceCount).forEach(this::downloadInvoices);
    }
}
