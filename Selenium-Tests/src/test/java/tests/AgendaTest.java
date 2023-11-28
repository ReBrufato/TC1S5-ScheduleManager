package tests;

import org.assertj.core.api.SoftAssertions;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgendaTest {

    private WebDriver driver;
    static String indexPath;
    static String registerPath;
    static String editPath;

    private PessoaFaker pessoa;

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

        String basePathWindows = indexSource.toString();
        String basePathLinux = "file:///home/ronaldo/IdeaProjects/TC1S5-ScheduleManager/src";
        indexPath = basePathWindows + "/index.html";
        registerPath = basePathWindows + "/register.html";
        editPath = basePathWindows + "/edit.html";

    }

    @BeforeEach
    void init(){

        String driverWindows = "src/test/resources/drivers/chromedriverWindows.exe";
        String driverLinux = "src/test/resources/drivers/chromedriver";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver",driverWindows);
        this.driver = new ChromeDriver(options);

        this.pessoa = new PessoaFaker();
    }

    @AfterEach
    void quitDriver(){
        this.driver.quit();
    }

    @Test
    @DisplayName("should alert success when register a valid user")
    void shouldAlertSuccessWhenRegisterAValidUser() {

        driver.get(registerPath);
        var cadastroPage = new CadastroPage(driver);

        String mensagemAlerta = cadastroPage.getAlertMessageText(pessoa.nome, pessoa.email, pessoa.phone);

        assertThat(mensagemAlerta).isEqualTo("Contato salvo com sucesso!");
    }

    @Test
    @DisplayName("should add one more user to the list if success on insertion")
    void shouldAddOneUserToListIfSuccessInsertion() {

        driver.get(indexPath);
        var contatosPage = new ContatosPage(driver);
        var cadastroPage = new CadastroPage(driver);

        var beforeInsertion = contatosPage.getContatos().size();

        cadastroPage.cadastroUserValidoFromIndex(pessoa.nome, pessoa.email, pessoa.phone);

        var afterInsertion = contatosPage.getContatos().size();

        assertThat(afterInsertion).isEqualTo(beforeInsertion + 1);
    }

    @Test
    @DisplayName("should not save contact with null name")
    void shouldNotSaveContactWithBlancName() {

        driver.get(registerPath);
        var cadastroPage = new CadastroPage(driver);
        String alertMessage = cadastroPage.getAlertMessageText("", pessoa.email, pessoa.phone);

        assertThat(alertMessage).isEqualTo("Nome inválido!");
    }

    @Test
    @DisplayName("should not save contact with null email")
    void shouldNotSaveContactWithBlancEmail() {
        driver.get(registerPath);
        var cadastroPage = new CadastroPage(driver);
        String alertMessage = cadastroPage.getAlertMessageText(pessoa.nome, "", pessoa.phone);

        assertThat(alertMessage).isEqualTo("Email inválido!");
    }

    @Test
    @DisplayName("should not save contact with null phone")
    void shouldNotSaveContactWithBlancPhone() {
        driver.get(registerPath);
        var cadastroPage = new CadastroPage(driver);
        String alertMessage = cadastroPage.getAlertMessageText(pessoa.nome, pessoa.email, "");

        assertThat(alertMessage).isEqualTo("Telefone inválido!");
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
    @DisplayName("should contain user in the home page after valid register")
    void shouldContainUserInTheHomePageAfterValidRegister(){

        driver.get(indexPath);
        var cadastroPage = new CadastroPage(driver);
        var contatosPage = new ContatosPage(driver);

        cadastroPage.cadastroUserValidoFromIndex(pessoa.nome, pessoa.email, pessoa.phone);

        List<ContatosComponent> contatos = contatosPage.getContato(c -> c.getNome().equals(pessoa.nome));
        assertThat(contatos).isNotEmpty();
    }

    @Test
    @DisplayName("should try to delete a person 2 and verify if the name on the alert is the same as the name of the user")
    void shouldTryToDeleteAPerson2AndVerifyName(){

        driver.get(indexPath);
        var contatosPage = new ContatosPage(driver);

        String userName = contatosPage.getContatoNameFromId("4");
        String alertMessage = contatosPage.getDeleteAlertMessageFromId("4");

        assertThat(alertMessage).isEqualTo("Tem certeza que quer deletar o contato de " + userName);
    }

    @Test
    @DisplayName("should try to delete a person 2 and then cancel")
    void shouldTryToDeleteAPerson2AndThenCancel(){

        driver.get(indexPath);
        var contatosPage = new ContatosPage(driver);

        var contactBeforeTryDelete = contatosPage.getContatos().size();

        contatosPage.dismissDeleteFromId("4");

        var contactAfterCancelDeletion = contatosPage.getContatos().size();

        assertThat(contactAfterCancelDeletion).isEqualTo(contactBeforeTryDelete);
    }

    @Test
    @DisplayName("should try to delete a person 2")
    void shouldTryToDeleteAPerson2(){

        driver.get(indexPath);
        var contatosPage = new ContatosPage(driver);

        var beforeDeleting = contatosPage.getContatos().size();

        String userName = contatosPage.getContatoNameFromId("4");
        String alertMessage = contatosPage.getDeleteAlertSuccessMessageFromId("4");

        var afterDeleting = contatosPage.getContatos().size();

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(alertMessage).isEqualTo("O contato de " + userName + " foi deletado com sucesso!");
        soft.assertThat(afterDeleting).isEqualTo(beforeDeleting -1);
        soft.assertAll();
    }
}
