package kg.eBook.ebookb5.security;

import kg.eBook.ebookb5.repositories.UserRepository;
import kg.eBook.ebookb5.security.JWT.TokenVerifierFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenVerifierFilter jwtFilter;

    @Bean
    AuthenticationProvider authenticationProvider(UserRepository personRepository) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService((email) -> (UserDetails) personRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "user with email = " + email + " not found!"
                )));
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    SecurityFilterChain authorization(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests(auth -> auth
                        .antMatchers("/swagger", "/swagger-ui/index.html").permitAll()
                        .antMatchers("/api/public/**").permitAll()
                        .anyRequest()
                        .permitAll()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
