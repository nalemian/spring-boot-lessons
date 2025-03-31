package ru.inno.nalemian.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.inno.nalemian.component.TestObjectComponent;
import ru.inno.nalemian.model.TestObject;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class TestObjectController {
    private final TestObjectComponent testObjectComponent;

    @GetMapping("/test-object")
    public Collection<TestObject> getAllObjects() {
        return testObjectComponent.getAll();
    }

    @GetMapping("/test-object/{id}")
    public TestObject getObjectById(@PathVariable int id) {
        TestObject object = testObjectComponent.getById(id);
        if (object == null) {
            throw new RuntimeException("Object not found");
        }
        return object;
    }

    @PostMapping("/test-object")
    public TestObject addOrUpdateObject(@RequestBody TestObject testObject) {
        return testObjectComponent.add(testObject);
    }

    @DeleteMapping("/test-object/{id}")
    public TestObject deleteObject(@PathVariable int id) {
        TestObject object = testObjectComponent.removeById(id);
        if (object == null) {
            throw new RuntimeException("Object not found");
        }
        return object;
    }
}
