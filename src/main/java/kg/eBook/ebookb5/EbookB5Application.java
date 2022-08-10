package kg.eBook.ebookb5;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@SpringBootApplication
public class EbookB5Application {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(EbookB5Application.class, args);
		System.out.println("Welcome colleagues, project name is eBook-B5!");
	}

	@GetMapping("/")
	public String greetingPage(){
		return "<h1>Welcome to eBook application!!!<h1/>";
	}

}
