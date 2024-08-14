package brockresource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import brockresource.service.UserService;

@Configuration
@EnableWebSecurity
public class AuthConfig {

    /*
     * It is replacing out-of-the-box UserDetailsService with our implementation.
     */
    @SuppressWarnings("unused")
    @Autowired
    private UserService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated())
                .formLogin(withDefaults())
                .httpBasic(withDefaults());

        return http.build();
    }
}
