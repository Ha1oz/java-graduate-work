package ru.skypro.homework.security;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.MyUserDetailsDto;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyUserDetails implements UserDetails{
    private MyUserDetailsDto myUserDetailsDto;
    /**
     *  Реализация регистрации новых пользователей и авторизация
     *  Главная цель  показать как можно ограничить доступ к различным страницам  сайта для пользователей с разными ролями.
     *  Ограничение доступа к страницам сайта в зависимости от роли пользователя.
     */
    public void setMyUserDetailsDto(MyUserDetailsDto myUserDetailsDto) {
        this.myUserDetailsDto = myUserDetailsDto;
    }

     /**
     *  В нашей реализации UserDetailsService происходит сопоставление полномочий.
     *   После того, как пользователь прошел аутентификацию,
     *   наш метод getAuthorities() заполняет и возвращает объект UserDetails.
     *   Optional.of  бросит исключение NullPointerException, если ему передать значение null в качестве параметра.
     */


    /**
     *  //ROLE_ это префикс по умолчанию, используемый RoleVoter при проверке ролей в вашем методе контроллера, например @Secured("ROLE_ADMIN")
     *                 .map(role -> "ROLE_" + role)
     *  Spring Security будет проверять, имеет ли аутентифицированный пользователь требуемые роли
     *  Используя эти сопоставленные роли. SimpleGrantedAuthority это просто реализация GrantedAuthority
     *  Эта строка создает новый объект SimpleGrantedAuthority класса,  SimpleGrantedAuthority это просто реализация GrantedAuthority, и возвращает его.
     *                 .map(SimpleGrantedAuthority::new)
     *  коллекции singleton вернут сериализованные и неизменяемые java Set, List и Map соответственно, содержащие данный объект
     *                 .map(Collections::singleton)
     *   иначе вернет пустую коллекцию .orElse(Collections.emptySet());     *
     *   После того, как пользователь прошел аутентификацию,
     *   наш метод getAuthorities() заполняет и возвращает объект UserDetails.
     *   Optional.of  бросит исключение NullPointerException, если ему передать значение null в качестве параметра.
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(myUserDetailsDto)
                .map(MyUserDetailsDto::getRole)
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .map(Collections::singleton)
                .orElse(Collections.emptySet());
    }

    @Override
    public String getPassword() {
        return Optional.ofNullable(myUserDetailsDto)
                .map(MyUserDetailsDto::getPassword)
                .orElse(null);
    }

    @Override
    public String getUsername() {
        return Optional.ofNullable(myUserDetailsDto)
                .map(MyUserDetailsDto::getEmail)
                .orElse(null);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
