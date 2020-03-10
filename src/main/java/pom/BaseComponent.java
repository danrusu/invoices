package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class BaseComponent {

    private By context;
    private WebDriver driver;

    BaseComponent(WebDriver driver, By componentSelector) {
        this.driver = driver;
        this.context = componentSelector;
    }

    private WebElement findElement(By selector){
        return driver.findElement(context).findElement(selector);
    }

    void click(By elementSelector){
        findElement(elementSelector).click();
    }
}
