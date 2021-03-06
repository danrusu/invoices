package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getAnonymousLogger;

class Driver {

    private Driver() { }

    private static final String FILE_NAME_TIMESTAMP_FORMAT = "yyyy_MM_dd_HH_mm_ss";
    private static String downloadFolder;

    private static String getTimestamp(){
        return new SimpleDateFormat(FILE_NAME_TIMESTAMP_FORMAT).format(
                Calendar.getInstance().getTime());
    }

    private static void createDir(Path newFolderPath) {
        if (! Files.exists(newFolderPath)) {
            try {
                Files.createDirectory(newFolderPath);
            } catch (IOException e) {
                getAnonymousLogger().log(SEVERE, Arrays.toString(e.getStackTrace()));
            }
        }
    }

    static WebDriver getChromeDriver(){

        String userDir = System.getProperty("user.dir");

        Path downloadFolderPath = Paths.get(userDir,
                "download",
                getTimestamp() + "_invoices");

        createDir(downloadFolderPath);

        String driverExecutablePath = Paths
                .get(userDir, "chromedriver.exe")
                .toString();

        System.setProperty("webdriver.chrome.driver", driverExecutablePath);

        downloadFolder = downloadFolderPath.toString();

        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        String downloadFolderMessage = "Browser download folder: " + downloadFolder;
        getAnonymousLogger().log(INFO, downloadFolderMessage);
        chromePrefs.put("download.default_directory", downloadFolder);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);

        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        return driver;
    }

    static String getDownloadFolder() {
        return downloadFolder;
    }
}
