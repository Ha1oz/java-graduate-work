package ru.skypro.homework.filter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.skypro.homework.dto.AdsDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Получить Basic Auth выбрана потому, что с ней сразу происходит и аутентификация, и авторизация.
 * То есть не надо отправлять запрос дважды, как это было бы с формой логина
 * (в этом случае пришлось бы первым запросом отправлять form-data на /login, а потом уже тестировать /user).
 * Поскольку OncePerRequestFilter поддерживает только HTTP-запросы, то  нет необходимости приводить объекты запроса и ответа,
 * как мы делаем при реализации интерфейса Filter.
 */



@Component
public class BasicAuthCorsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
//                  httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
//                  httpServletResponse.addHeader("Access-Control-Allow-Headers", "DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,Authorization,If-Modified-Since,Cache-Control,Content-Type");
//                  httpServletResponse.addHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
//                  httpServletResponse.addHeader("charset", "utf-8");
//                  httpServletResponse.addHeader("Access-Control-Allow-Methods","GET, POST, PUT, OPTIONS, DELETE");
// For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

