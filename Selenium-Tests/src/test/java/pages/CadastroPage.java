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

    public void cadastroUserValido(String nome, String email, String telefone) {

        driver.findElement(nameContact).sendKeys(nome);
        driver.findElement(emailContact).sendKeys(email);
        driver.findElement(phoneContact).sendKeys(telefone);
        driver.findElement(submit).click();

    }

    public void cadastroUserValidoFromIndex(String nome, String email, String telefone){
        driver.findElement(By.xpath("//*[@id=\"nav\"]")).click();

        this.cadastroUserValido(nome, email, telefone);

        driver.switchTo().alert().accept();
        driver.findElement(By.xpath("//*[@id=\"nav\"]")).click();
    }

    public String getAlertMessageText(String nome, String email, String telefone){

        this.cadastroUserValido(nome, email, telefone);
        String alertMessage = driver.switchTo().alert().getText();

        return alertMessage;
    }

    public void cadastroUserAndReturnToIndex(String nome, String email, String telefone){
        this.cadastroUserValido(nome, email, telefone);
        driver.switchTo().alert().accept();
        driver.findElement(By.xpath("//*[@id=\"nav\"]")).click();
    }
}
