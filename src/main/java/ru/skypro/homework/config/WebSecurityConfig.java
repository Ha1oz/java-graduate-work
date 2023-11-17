package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
/**
 *   Аннотация @Configuration (это означает, что данный класс является конфигурационным)
 *   и @EnableWebSecurity (это означает, что данный класс является содержит настройки для защиты веб-приложения)
 *  @EnableGlobalMethodSecurity(prePostEnabled = true)
 *  - без нее аннотация @Preauthorize работать не будет.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register"
    };

    /**
     *   Доступ только для не зарегистрированных пользователей
     *                     .antMatchers("/registration").not().fullyAuthenticated()
     *  Доступ только для пользователей с ролью Администратор
     *                     .antMatchers("/admin/**").hasRole("ADMIN")
     *                     .antMatchers("/news").hasRole("USER")
     *   Доступ разрешен всем пользователей
     *                      .antMatchers("/", "/resources/**").permitAll()
     *  Все остальные страницы требуют аутентификации
     *                 .anyRequest().authenticated()
     *                 .and()
     *  Настройка для входа в систему
     *                      .formLogin()
     *                     .loginPage("/login")
     *      Перенарпавление на главную страницу после успешного входа
     *                      .defaultSuccessUrl("/")
     *                      .permitAll()
     *                 .and()
     *                      .logout()
     *                     .permitAll()
     *                      .logoutSuccessUrl("/");
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(
                        (authorization) ->
                                authorization
                                        .mvcMatchers(AUTH_WHITELIST)
                                        .permitAll()
                                        .mvcMatchers(HttpMethod.GET, "/ads", "/ads/image/*", "/users/image/*")
                                        .permitAll()
                                        .mvcMatchers("/ads/**", "/users/**")
                                        .authenticated()
                )
                .cors()
                .and()
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(withDefaults());
        return http.build();
    }
    /**
     *  Хранить пароли без хеширования является грубейшим нарушением правил безопасности,
     *  поэтому нам необходимо добавить процесс хеширования пароля в нашу систему.
     *  На даннай момент рекомендуемым является алгоритм Bcrypt.
     *  Один из двух   способов - создаем Bean, который будет возвращать объект Encoder`
     *  а и добавим его как метод конфигурационного класса.
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}