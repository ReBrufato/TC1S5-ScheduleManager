package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ContatosComponent {
    private WebElement root;

    public ContatosComponent(WebElement root){
        this.root = root;
    }

    public String getNome(){
        return root.findElement(By.tagName("span")).getText();
    }
}
