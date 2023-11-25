package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class AgendaTest {

    private WebDriver driver;
    static String indexPath;

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
        indexSource.add("index.html");

        indexPath = indexSource.toString();
    }

    @BeforeEach
    void init(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
        this.driver = new ChromeDriver(options);

    }

    @AfterEach
    void quitDriver(){
        this.driver.quit();
    }

    @Test
    @DisplayName("Should open and close chrome browser using Manager")
    void shouldOpenAndCloseChromeBrowserUsingManager() throws InterruptedException {

        driver.get(indexPath);
        Thread.sleep(10000);
        driver.quit();
    }
}
