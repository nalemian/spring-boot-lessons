package ru.inno.nalemian.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.inno.nalemian.component.TypeStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentService {
    @Autowired
    private List<TypeStrategy> types;
    private final Map<String, TypeStrategy> strategiesMap = new HashMap<>();

    @PostConstruct // выполняется один раз после создания бина (и в этом случае после заполнения types)
    public void strategiesMapInitialization() {
        for (TypeStrategy type : types) {
            strategiesMap.put(type.getNeededType(), type);
        }
    }

    public TypeStrategy getStrategy(String type) {
        return strategiesMap.get(type);
    }
}
