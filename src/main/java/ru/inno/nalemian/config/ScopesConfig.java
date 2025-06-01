package ru.inno.nalemian.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import ru.inno.nalemian.model.Document;

@Configuration
public class ScopesConfig {
    // Бин с "Prototype scope"
    // При каждом запросе к контексту создается новый экземпляр
    @Bean
    @Scope("prototype")
    public Document documentPrototype() {
        return new Document();
    }

    // Бин с "Request scope"
    // Создается новый экземпляр бина при каждом HTTP-запросе
    @Bean
    @RequestScope
    public Document requestScopedBean() {
        return new Document();
    }

    // Бин с "Request scope"
    // Создается один экземпляр бина для каждой HTTP-сессии
    @Bean
    @SessionScope
    public Document sessionScopedBean() {
        return new Document();
    }
}
