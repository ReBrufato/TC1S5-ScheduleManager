package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    private By listaContato = By.id("contacts");
    private By itemContatos = By.className("item");

    public HomePage(WebDriver driver){
        this.driver = driver;
        if (!driver.getTitle().equals("Agenda de contatos")) {
            throw new IllegalStateException("Esta não é a página Agenda de contatos" +
                    " a págiana enviada é: " + driver.getCurrentUrl());
        }
    }
}

