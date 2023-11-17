***_Materials for course work by students of the java developer profession_***.

**_The work was done:_**

_Kastalskaya Elena_

_Oleg Barkalov_

_Dmitry Kovalev_

В работе использована классическая архитектура веб-приложения. 
Контроллер, сервис, интерфейсы , маппер (который мапит dto в сущность и обратно),
dto  и сущности и вспомогательные классы.

Преобразование в сервисе. Этот подход подразумевает применение сущности только 
для работы с репозиторием. Плюс подхода в том, что dto намного легче сущности
в плане зависимостей, и гонять его между сервисами намного проще, да и работать 
с dto намного проще. Не надо задумываться о консистентности данных,
lazy initialization и прочих тонкостях работы с сущностями. 
Так же, это позволяет контролировать все запросы в базу, если нам потребовалась 
зависимость, мы просто берём её по данным dto, избегая, таким образом, случаев,
когда для получения int-поля приходится тянуть 
из базы всю сущность с деревом зависимостей и хранить это в памяти.

Объект передачи данных, обычно называемый DTO, – это объект, используемый для
проверки данных и определения
структуры данных, отправляемых в ваши приложения.
DTO – это класс, поэтому он имеет тот же синтаксис, что и класс.
DTO или объекты передачи данных — это объекты, которые переносят данные между процессами,
чтобы уменьшить количество вызовов методов. 
Паттерн был впервые представлен Мартином Фаулером в его книге EAA.

Фаулер объяснил, что основная цель шаблона — сократить количество обращений к 
серверу за счет группирования нескольких параметров в одном вызове.
Это снижает нагрузку на сеть при таких удаленных операциях.
Еще одним преимуществом является инкапсуляция логики сериализации
(механизм, который переводит структуру объекта и данные в определенный формат,
который можно хранить и передавать). Он обеспечивает единую точку изменения нюансов
сериализации. Он также отделяет модели предметной
области от уровня представления, позволяя им изменяться независимо друг от друга.
DTO представляет собой модель, отправляемую от или к клиенту API.

Следовательно, небольшие различия заключаются либо в том, чтобы упаковать вместе запрос,
отправленный на сервер,
либо в оптимизации ответа клиента:
public class UserDTO {

    private String name;
    private List<String> roles;

}

Вышеупомянутый DTO предоставляет клиенту только необходимую информацию, скрывая пароль,
например, из соображений безопасности.
MapStruct работает следующим образом.
Следующий DTO группирует все данные, необходимые для создания пользователя,
и отправляет их на сервер в одном запросе, что оптимизирует взаимодействие с API:

public class UserCreationDTO {

    private String name;
    private String password;
    private List<String> roles;
 
}


С  помощью MapStruct создаются специальные мапперы под каждую сущность, 
внутри которых определяются правила конвертирования из DTO или в DTO в зависимости
от потребностей.
Дальше эти мапперы используются в нужных местах, сводя преобразования к одной строчке.
MapStruct самостоятельно написал тот код, который мы до этого писали руками. Но как он это сделал?
MapStruct сравнивает методы обоих классов и автоматически распознает те, что совпадают. Кроме этого,
MapStruct автоматически пытается преобразовать типы, если они не совпадают. В большинстве случаев это
работает автоматически, но там где нет,
всегда есть возможность дописать правила конвертации и преобразования типов.

Остальные моменты более подробно изложены в комментариях к коду настоящей работы. 
Продолжение совершенствования кода настоящего приложения следует...

_Libraries and materials used:_

(https://stackoverflow.com/questions/65112406/intellij-idea-mapstruct-java-internal-error-in-the-mapping-processor-java-lang/65113549#65113549)
(https://stackoverflow.com/questions/66591176/java-lang-nullpointerexception-cannot-invoke-java-net-url-toexternalform-be)
(https://docs.spring.io/spring-security/reference/servlet/authorization/architecture.html)
(https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html)
(https://sky.pro/media/ponimanie-transactional-v-spring-framework/)
(https://www.baeldung.com/transaction-configuration-with-jpa-and-spring)
(https://www.javaguides.net/2022/12/spring-boot-mapstruct-example-tutorial.html)
(https://russianblogs.com/article/76591107381/)
(https://www.tutorialsbuddy.com/validating-request-input-in-spring-boot#gsc.tab=0)
(https://www.baeldung.com/java-validation)
(https://www.javaguides.net/2022/12/spring-boot-mapstruct-example-tutorial.html)
(https://stackoverflow.com/questions/37615034/spring-security-spring-boot-how-to-set-roles-for-users)
(https://babarowski.com/blog/mock-authentication-with-custom-userdetails/)
(https://urvanov.ru/2018/09/10/разница-между-optional-of-и-optional-ofnullable/)
(https://stackoverflow.com/questions/40286549/spring-boot-security-cors)
(https://stackoverflow.com/questions/44671457/what-is-the-use-of-enablewebsecurity-in-spring)
(https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html)
(https://sysout.ru/zashhita-metodov-annotatsiya-preauthorize/)
(https://www.baeldung.com/get-user-in-spring-security)
(https://javatutor.net/articles/loading-and-saving-images-using-java-io-library)
(https://stackoverflow.com/questions/33214214/how-to-get-the-real-extension-type-of-multipart-file)
(https://www.baeldung.com/java-images)
(https://javarush.com/groups/posts/1472-world-of-bytes-1--rabota-s-izobrazhenijami)
(https://www.programmersought.com/article/524511059381/)
(https://russianblogs.com/article/49573556927/)
(https://www.demo2s.com/java/spring-multipartfile-getoriginalfilename-return-the-original-filenam.html)
(https://programtalk.com/java-more-examples/org.springframework.web.multipart.MultipartFile/?ipage=12)
(https://www.baeldung.com/java-db-storing-files)
(https://www.baeldung.com/java-file-extension)
(https://www.baeldung.com/java-filename-without-extension)
(http://www.java2s.com/example/java-api/org/springframework/util/stringutils/getfilenameextension-1-0.html)
(https://bushansirgur.in/spring-boot-upload-file-to-aws-s3/)
(https://stackoverflow.com/questions/33214214/how-to-get-the-real-extension-type-of-multipart-file)
(https://www.demo2s.com/java/java-files-write-path-path-byte-bytes-openoption.html)
(https://javascopes.com/java-how-to-create-and-write-to-a-file-92a101e0/)
(https://howtodoinjava.com/java/io/java-write-to-file/)
(https://www.baeldung.com/spring-boot-thymeleaf-image-upload)
(https://java-lessons.ru/files/write-to-file)
(https://www.demo2s.com/g/java/how-to-write-file-content-using-java-nio-file-path.html)
(https://habr.com/ru/articles/482552/)
(https://www.baeldung.com/get-user-in-spring-security)
(https://habr.com/ru/articles/482552/)
(https://stackoverflow.com/questions/1016278/is-this-the-best-way-to-rewrite-the-content-of-a-file-in-java)
(https://sky.pro/media/rabota-metoda-randomuuid-v-java/)
(https://devwithus.com/download-upload-files-with-spring-boot/)
(https://bushansirgur.in/spring-boot-file-upload-and-download-with-database-2/)
(https://stackoverflow.com/questions/54376041/how-to-resolve-request-method-get-not-supported)
(https://codingbat.com/prob/p187868)
(https://www.baeldung.com/spring-data-jpa-exception-no-property-found-for-type)
(https://ru.stackoverflow.com/questions/1175685/Ошибка-при-создании-бина-jparepository-в-spring-boot-приложении)
(https://www.baeldung.com/sprint-boot-multipart-requests)
(https://www.demo2s.com/java/spring-mediatype-multipart-form-data-value.html)
(https://stackoverflow.com/questions/30060609/hibernate-psqlexception-bad-value-for-type-int-admin)
(https://sysout.ru/zashhita-metodov-annotatsiya-preauthorize/)
(https://www.baeldung.com/spring-security-authentication-with-a-database#UserDetailsService-1)
(https://www.inmyroom.ru/posts/37491-9-krutyh-idej-kak-ehffektno-i-nedorogo-preobrazit-kvartiru)
(https://for-each.dev/lessons/b/-java-dto-pattern)
 





 





 


 


 

