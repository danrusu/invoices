package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void setInputField(By inputSelector, String text, SearchContext searchContext){
        searchContext.findElement(inputSelector).sendKeys(text);
    }

    public void setInputField(By inputSelector, String text){
        setInputField(inputSelector, text, driver);
    }

    public void click(By elementSelector, SearchContext searchContext){
        searchContext.findElement(elementSelector).click();
    }

    public void click(By elementSelector){
        click(elementSelector, driver);
    }

}
