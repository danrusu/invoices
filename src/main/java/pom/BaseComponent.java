package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public class BaseComponent {

    private SearchContext context;

    public BaseComponent(WebDriver driver, By componentSelector) {
        this.context = driver.findElement(componentSelector);
    }

    public void setInputField(By inputSelector, String text){
        context.findElement(inputSelector).sendKeys(text);
    }

    public void click(By elementSelector){
        context.findElement(elementSelector).click();
    }
}
