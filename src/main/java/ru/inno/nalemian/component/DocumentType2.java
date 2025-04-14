package ru.inno.nalemian.component;

import org.springframework.stereotype.Component;
import ru.inno.nalemian.model.Document;

@Component
public class DocumentType2 implements TypeStrategy {

    @Override
    public String process(Document document) {
        return "test" + document.getValue() + "test";
    }

    @Override
    public String getNeededType() {
        return "2";
    }
}