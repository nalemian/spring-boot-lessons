package ru.inno.nalemian.component;

import org.springframework.stereotype.Component;
import ru.inno.nalemian.model.Document;

@Component
public class DocumentType1Strategy implements TypeStrategy {

    @Override
    public String process(Document document) {
        return "1" + document.getValue() + "1";
    }

    @Override
    public String getNeededType() {
        return "1";
    }
}
