package com.example.app;

import com.example.app.app1.services.ColorPrinter;
import com.example.app.app2.config.PizzaConfig;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class AppApplication implements CommandLineRunner {
	private final DataSource dataSource;

	public AppApplication(DataSource dataSource) {
        this.dataSource = dataSource;
	}

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
		restTemplate.execute("select 1");
	}

//	@Override
//	public void run(final String... args) throws Exception {
//		log.info(String.format("I want a %s crust pizza ,with %s and %s sauce",
//				pizzaConfig.getCrust(),
//				pizzaConfig.getSauce(),
//				pizzaConfig.getTopping()));
//	}
//
}
