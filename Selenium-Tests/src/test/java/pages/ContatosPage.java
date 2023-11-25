package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.function.Predicate;

public class ContatosPage extends HomePage {

    public ContatosPage(WebDriver driver) {
        super(driver);
    }

    public List<ContatosComponent> getContatos(){
        return driver.findElements(By.className("item"))
                .stream()
                .map(e -> new ContatosComponent(e))
                .toList();
    }
    
    public ContatosComponent getContato(Predicate<ContatosComponent> condition){
        return getContatos()
                .stream()
                .filter(condition)
                .findFirst()
                .orElseThrow();
    }

}
