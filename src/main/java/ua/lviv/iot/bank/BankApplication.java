package ua.lviv.iot.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ua.lviv.iot.bank.service.DBLoaderService;

@SpringBootApplication
public class BankApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BankApplication.class, args);
		context.getBean(DBLoaderService.class).dumpCsvToDB("data.csv");
	}
}
