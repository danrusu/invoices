package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

class BasePage {

    protected WebDriver driver;

    BasePage(WebDriver driver) {
        this.driver = driver;
    }

    void setInputField(By inputSelector, String text, SearchContext searchContext){
        searchContext.findElement(inputSelector).sendKeys(text);
    }

    void setInputField(By inputSelector, String text){
        setInputField(inputSelector, text, driver);
    }

    void click(By elementSelector, SearchContext searchContext){
        searchContext.findElement(elementSelector).click();
    }

    void click(By elementSelector){
        click(elementSelector, driver);
    }
}
