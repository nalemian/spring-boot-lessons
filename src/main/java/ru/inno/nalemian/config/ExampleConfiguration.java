package ru.inno.nalemian.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.inno.nalemian.component.TestComponent;

@Configuration
public class ExampleConfiguration {
    @Bean
    public TestComponent createTestComponent() {
        return new TestComponent();
    }
}
