package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    
    public List<ContatosComponent> getContato(Predicate<ContatosComponent> condition){
        List<ContatosComponent> contatos = getContatos()
                        .stream()
                        .filter(condition)
                        .collect(Collectors.toList());

        return contatos;
    }

}
