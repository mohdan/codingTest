package com.mytaxi;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mytaxi.domainobject.Role;
import com.mytaxi.domainobject.User;
import com.mytaxi.service.driver.UserService;
import com.mytaxi.util.LoggingInterceptor;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SuppressWarnings("deprecation")
@EnableSwagger2
@SpringBootApplication
public class MytaxiServerApplicantTestApplication extends WebMvcConfigurerAdapter
{

    public static void main(String[] args)
    {
        SpringApplication.run(MytaxiServerApplicantTestApplication.class, args);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
    }


    @Bean
    public Docket docket()
    {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName())).paths(PathSelectors.any())
            .build().apiInfo(generateApiInfo()).genericModelSubstitutes(Optional.class);
    }


    private ApiInfo generateApiInfo()
    {
        return new ApiInfo("mytaxi Server Applicant Test Service",
            "This service is to check the technology knowledge of a server applicant for mytaxi.",
            "Version 1.0 - mw", "urn:tos", "career@mytaxi.com", "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0");
    }


    @Bean
    public CommandLineRunner setupDefaultUser(UserService service)
    {
        return args -> {
            service.save(new User(
                "user", //username
                "user", //password
                Arrays.asList(new Role("USER"), new Role("ACTUATOR"))));
        };
    }


    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
