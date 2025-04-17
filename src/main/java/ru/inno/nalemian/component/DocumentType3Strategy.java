package ru.inno.nalemian.component;

import org.springframework.stereotype.Component;
import ru.inno.nalemian.model.Document;

@Component
public class DocumentType3Strategy implements TypeStrategy {

    @Override
    public String process(Document document) {
        String value = document.getValue();
        try {
            return value.substring(0, 40);
        } catch (IndexOutOfBoundsException e) {
            return value;
        }
    }

    @Override
    public String getNeededType() {
        return "3";
    }
}