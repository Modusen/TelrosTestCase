package com.vaadin.TelrosTestCase.config;

import com.vaadin.TelrosTestCase.view.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().disable()
                .authorizeHttpRequests(authorization -> authorization.requestMatchers("/logging", "/").permitAll()
                        .requestMatchers("/users").hasAnyRole("ADMIN"))
                .formLogin().loginPage("/logging").loginProcessingUrl("/login")
                .defaultSuccessUrl("/users");
//                .authorizeHttpRequests().requestMatchers("/login").permitAll()
//                        .anyRequest().authenticated()
//                .and().formLogin().loginPage("/login").loginProcessingUrl("/log-in")
//                .defaultSuccessUrl("/users", true)
        ;

        super.configure(http);

        setLoginView(http, LoginView.class);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public PasswordEncoder passwordEncoderTest(){
        return NoOpPasswordEncoder.getInstance();
    }
}
