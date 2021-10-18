package com.library.databaseH2;

import com.library.databaseH2.config.BookListConfiguration;
import com.library.databaseH2.console_ui.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class DatabaseH2Application {

	public static void main(String[] args) {
		ApplicationContext applicationContext = getApplicationContext();

		ProgramMenu programMenu = applicationContext.getBean(ProgramMenu.class);

		SpringApplication.run(DatabaseH2Application.class, args);
		while (true) {
			programMenu.printMenu();
			int input = programMenu.getMenuNumber();
			programMenu.executeMenuNumber(input);
		}
	}

	private static ApplicationContext getApplicationContext() {
		return new AnnotationConfigApplicationContext(BookListConfiguration.class);
	}
}
