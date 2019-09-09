package uk.org.landeg.jat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class JavaAppTemplateApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(JavaAppTemplateApplication.class, args);
	}

}
