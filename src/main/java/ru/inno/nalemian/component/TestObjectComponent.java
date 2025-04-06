package ru.inno.nalemian.component;

import org.springframework.stereotype.Component;
import ru.inno.nalemian.model.TestObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class TestObjectComponent {
    private final Map<Integer, TestObject> storage = new HashMap<>();

    public TestObject getById(int id) {
        return storage.get(id);
    }

    public Collection<TestObject> getAll() {
        return storage.values();
    }

    public TestObject add(TestObject testObject) {
        storage.put(testObject.getId(), testObject);
        return testObject;
    }

    public TestObject removeById(int id) {
        return storage.remove(id);
    }

}
