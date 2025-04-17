package ru.inno.nalemian.service;

import org.springframework.stereotype.Service;
import ru.inno.nalemian.component.TypeStrategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private final Map<String, TypeStrategy> strategiesMap;

    public DocumentService(List<TypeStrategy> types) {
        strategiesMap = types.stream()
                .collect(Collectors.toMap(TypeStrategy::getNeededType, type -> type));
    }

    public TypeStrategy getStrategy(String type) {
        return strategiesMap.get(type);
    }
}
