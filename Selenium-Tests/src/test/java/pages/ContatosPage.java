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

    public String getContatoNameFromId(String id) {

        String userName = driver.findElement(By.xpath("//*[@id=" + id + "]/p[1]/span")).getText();
        return userName;
    }

    public void deleteContatoFromId(String id){
        driver.findElement(By.xpath("//*[@id=" + id + "]/button[2]")).click();
    }

    public String getDeleteAlertMessageFromId(String id){
        this.deleteContatoFromId(id);
        String alertMessage = driver.switchTo().alert().getText();
        return alertMessage;
    }

    public String getDeleteAlertSuccessMessageFromId(String id){
        this.deleteContatoFromId(id);
        driver.switchTo().alert().accept();
        String alertMessage = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        return alertMessage;
    }

    public void dismissDeleteFromId(String id){
        this.deleteContatoFromId(id);
        driver.switchTo().alert().dismiss();
    }

}
