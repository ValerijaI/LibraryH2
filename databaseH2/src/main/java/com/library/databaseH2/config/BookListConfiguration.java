package com.library.databaseH2.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.library.databaseH2")
//@PropertySource(value = "classpath:application.properties")
public class BookListConfiguration {

}
