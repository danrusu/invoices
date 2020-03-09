package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.stream.IntStream;

import static base.ThreadUtils.sleep;
import static base.ThreadUtils.sleep;

public class InvoicesPage extends BasePage {

    public static long ACTIONS_TIMEOUT = 1000;

    public InvoicesPage(WebDriver driver) {
        super(driver);
    }

    private By pageHeader = By.xpath("//h1[text()=\"INVOICES\"]");

    private By invoices = By.cssSelector("xnf-facture-item");
    private String invoiceDownloadLinkXpathTemplate = "(//xnf-facture-item//a[text()=\"Download\"])[@invoiceIndex]";

    public void clickInvoiceDownloadLink(int invoiceIndex){
        By invoiceDownloadLink = By.xpath(invoiceDownloadLinkXpathTemplate
                .replaceAll("@invoiceIndex", "" + invoiceIndex)
        );
        click(invoiceDownloadLink);
    }

    public void downloadInvoices(int invoiceIndex){
        System.out.println("Download invoice #" + invoiceIndex);

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

    public int getInvoiceCount() {
        return driver.findElements(invoices).size();
    }

    public void downloadAllInvoices() {
        int invoiceCount = getInvoiceCount();
        System.out.println(invoiceCount + " invoices to download!");
        IntStream.rangeClosed(1, invoiceCount).forEach(this::downloadInvoices);
    }
}
