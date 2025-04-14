package ru.inno.nalemian.component;

import org.springframework.stereotype.Component;
import ru.inno.nalemian.model.Document;

@Component
public interface TypeStrategy {
    String process(Document document);

    String getNeededType();
}
