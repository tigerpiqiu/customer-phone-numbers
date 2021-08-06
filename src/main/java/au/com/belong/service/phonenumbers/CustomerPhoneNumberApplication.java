package au.com.belong.service.phonenumbers;

import au.com.belong.service.phonenumbers.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ApplicationConfiguration.class)
public class CustomerPhoneNumberApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(CustomerPhoneNumberApplication.class);
        springApplication.run(args);
    }
}
