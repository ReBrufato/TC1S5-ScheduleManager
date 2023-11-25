package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CadastroPage {
    private WebDriver driver;

    private By nameContact = By.name("nameContact");
    private By emailContact = By.name("emailContact");
    private By phoneContact = By.name("phoneContact");
    private By submit = By.cssSelector("input[type='submit']");
    private By retornoIndex = By.id("nav");

    public CadastroPage(WebDriver driver) {
        this.driver = driver;
        if (!driver.getTitle().equals("Agenda de contatos")) {
            throw new IllegalStateException("Esta não é a página Agenda de contatos" +
                    " a págiana enviada é: " + driver.getCurrentUrl());
        }
    }

    public HomePage cadastroUserValido(String nome, String email, String telefone) throws InterruptedException {
        driver.findElement(nameContact).sendKeys(nome);
        driver.findElement(emailContact).sendKeys(email);
        driver.findElement(phoneContact).sendKeys(telefone);
        driver.findElement(submit).click();
        driver.switchTo().alert().accept();
        driver.findElement(retornoIndex).click();
        return new HomePage(driver);
    }
}
