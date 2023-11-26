package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.CadastroPage;
import pages.ContatosComponent;
import pages.ContatosPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;

public class AgendaTest {

    private WebDriver driver;
    static String indexPath;
    static String registerPath;
    static String editPath;

    @BeforeAll
    static void getIndexPath(){
        List<String> folders = new ArrayList<>();
        String diretorioDeTrabalho = System.getProperty("user.dir");

        Arrays.stream((diretorioDeTrabalho.split("\\\\")))
                .toList()
                .stream()
                .forEach(f -> folders.add(f));


        folders.remove(folders.size() - 1);

        StringJoiner indexSource = new StringJoiner("\\\\");
        for (String folder : folders ) {
            indexSource.add(folder);
        }
        indexSource.add("src");

        String basePath = "file:///home/ronaldo/IdeaProjects/TC1S5-ScheduleManager/src";
        indexPath = basePath + "/index.html";
        registerPath = basePath + "/register.html";
        editPath = basePath + "/edit.html";

    }

    @BeforeEach
    void init(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver");
        this.driver = new ChromeDriver(options);
    }

    @AfterEach
    void quitDriver(){
        this.driver.quit();
    }

    @Test
    @DisplayName("should alert success when register a valid user")
    void shouldAlertSuccessWhenRegisterAValidUser() {
        driver.get(registerPath);
        var page = new CadastroPage(driver);
        page.cadastroUserValido("josenildo", "josenildo@josenildo.com", "+5516999999921");
        String mensagemAlerta = driver.switchTo().alert().getText();

        assertThat(mensagemAlerta).isEqualTo("Contato salvo com sucesso!");

    }
    
    @Test
    @DisplayName("should return list of contatos from homepage")
    void shouldReturnListOfContatosFromHomepage() {

        driver.get(indexPath);
        var contatosPage = new ContatosPage(driver);
        var contatos = contatosPage.getContatos();
        assertThat(contatos.size()).isEqualTo(4);
    }
    
    @Test
    @DisplayName("should contain user in the home page")
    void shouldContainUserInTheHomePage(){

        driver.get(indexPath);
        var contatosPage = new ContatosPage(driver);
        List<ContatosComponent> contatos = contatosPage.getContato(c -> c.getNome().equals("Maria da Silva"));
        assertThat(contatos).isNotEmpty();
    }

    @Test
    @DisplayName("should try to delete a person 2 and verify if the name on the alert is the same as the name of the user")
    void shouldTryToDeleteAPerson2AndVerifyName(){

        driver.get(indexPath);
        String userName = driver.findElement(By.xpath("//*[@id=\"4\"]/p[1]/span")).getText();
        driver.findElement(By.xpath("//*[@id=\"4\"]/button[2]")).click();

        String mensagemAlerta = driver.switchTo().alert().getText();

        assertThat(mensagemAlerta).isEqualTo("Tem certeza que quer deletar o contato de " + userName);
    }
}
