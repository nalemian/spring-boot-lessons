package ru.inno.nalemian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.inno.nalemian.component.TypeStrategy;
import ru.inno.nalemian.model.Document;
import ru.inno.nalemian.service.DocumentService;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping
    public String processDocument(@RequestBody Document document) {
        String type = document.getType();
        TypeStrategy strategy = documentService.getStrategy(type);
        if (strategy == null) {
            throw new RuntimeException("Type not found");
        }
        return strategy.process(document);
    }
}
