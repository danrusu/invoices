package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{

    public static final String URL = "https://www.sfrbusiness.fr/espace-client/authentification";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Selectors
    private By loginName = By.cssSelector("input[formcontrolname=userName]");
    private By loginPassword = By.cssSelector("input[formcontrolname=password]");
    private By loginButton = By.cssSelector("button[type=submit]");

    // Actions
    public void login(String name, String password){
        setInputField(loginName, name);
        setInputField(loginPassword, password);
        click(loginButton);
    }
}
