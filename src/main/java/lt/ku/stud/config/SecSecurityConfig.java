package lt.ku.stud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig {


    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails admin=
                User
                        .withUsername("admin")
                        .password( passwordEncoder().encode("LabasLabas") )
                        .roles("ADMIN")
                        .build();
        UserDetails user=
                User
                        .withUsername("user")
                        .password( passwordEncoder().encode("LabasRytas") )
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(admin, user);


    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
               .csrf().disable()
               .authorizeHttpRequests( request->
                       request
                               .anyRequest().authenticated()
               )
               .formLogin( form ->
                       form
                               .loginPage("/login")
                               .permitAll()
                       )
               .logout( logout ->
                       logout
                               .permitAll()
               );

       return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
