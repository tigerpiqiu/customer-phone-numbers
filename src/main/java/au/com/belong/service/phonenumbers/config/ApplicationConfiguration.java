package au.com.belong.service.phonenumbers.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@ComponentScan(
        basePackages = {
                "au.com.belong.service.phonenumbers"
        })
@Configuration
@EnableSwagger2
public class ApplicationConfiguration {

}
