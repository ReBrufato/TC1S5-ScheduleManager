package tests;

import com.github.javafaker.Faker;

import java.util.Random;

public class PessoaFaker {

    protected String nome;
    protected String email;
    protected String phone;

    public PessoaFaker() {

        Faker faker = new Faker();
        
        String nome = faker.name().fullName().replace(".", "");
        this.nome = nome;
        
        String[] full_name = nome.split(" ");
        String email = full_name[0].toLowerCase() + "." +
                       full_name[1].toLowerCase() + "@dominio.com";

        this.email = email;


        Random r = new Random(System.currentTimeMillis());
        String phone = "+55";

        for (int i = 0; i < 11; i++) {
            phone += String.valueOf(r.nextInt(1, 9));
        }
        this.phone = phone;
    }
    
    
    
    
}
