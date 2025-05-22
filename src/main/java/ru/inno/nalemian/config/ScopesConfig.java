package ru.inno.nalemian.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import ru.inno.nalemian.model.Document;

@Configuration
public class ScopesConfig {
    @Bean
    @Scope("prototype")
    public Document documentPrototype() {
        return new Document();
    }

    @Bean
    @RequestScope
    public Document requestScopedBean() {
        return new Document();
    }

    @Bean
    @SessionScope
    public Document sessionScopedBean() {
        return new Document();
    }
}
