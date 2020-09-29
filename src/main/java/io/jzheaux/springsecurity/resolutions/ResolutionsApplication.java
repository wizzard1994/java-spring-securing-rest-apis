package io.jzheaux.springsecurity.resolutions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootApplication
public class ResolutionsApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(ResolutionsApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authz -> authz
                        .antMatchers(HttpMethod.GET, "/resolutions", "/resolution/**")
                        .hasAuthority("resolution:read")
                        .anyRequest().hasAuthority("resolution:write"))
                .httpBasic(basic -> {
                })
                .csrf().disable();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserRepositoryUserDetailsService(userRepository);
    }

}
