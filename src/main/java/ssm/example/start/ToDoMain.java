package ssm.example.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan("ssm.example.start")
public class ToDoMain {

	public static void main(String[] args) {
		SpringApplication.run(ToDoMain.class, args);
	}

}
