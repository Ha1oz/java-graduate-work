package ru.skypro.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.homework.dto.Role;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register",
            "/ads",
            "/ads/images/**"
    };

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user =
//                User.builder()
//                        .username("user@gmail.com")
//                        .password("password")
//                        .passwordEncoder(passwordEncoder::encode)
//                        .roles(Role.USER.name())
//                        .build();
//        UserDetails admin =
//                User.builder()
//                        .username("admin@gmail.com")
//                        .password("password1")
//                        .passwordEncoder(passwordEncoder::encode)
//                        .roles(Role.ADMIN.name())
//                        .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager(dataSource);

        User.UserBuilder userBuilder = User
                .withUsername("user@gmail.com")
                .password("password")
                .passwordEncoder(passwordEncoder()::encode)
                .roles(Role.USER.name());
        userDetailsService.createUser(userBuilder.build());

        User.UserBuilder adminBuilder = User
                .withUsername("admin@gmail.com")
                .password("password1")
                .passwordEncoder(passwordEncoder()::encode)
                .roles(Role.ADMIN.name());
        userDetailsService.createUser(adminBuilder.build());

        return new JdbcUserDetailsManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(
                        authorization ->
                                authorization
                                        .mvcMatchers(AUTH_WHITELIST)
                                        .permitAll()
                                        .mvcMatchers("/ads/**", "/users/**")
                                        .authenticated())
                .cors()
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
