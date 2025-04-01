package ru.inno.nalemian.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.inno.nalemian.component.TestObjectComponent;
import ru.inno.nalemian.model.TestObject;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test-object")
public class TestObjectController {
    private final TestObjectComponent testObjectComponent;

    @GetMapping
    public Collection<TestObject> getAllObjects() {
        return testObjectComponent.getAll();
    }

    @GetMapping("/{id}")
    public TestObject getObjectById(@PathVariable int id) {
        TestObject object = testObjectComponent.getById(id);
        if (object == null) {
            throw new RuntimeException("Object not found");
        }
        return object;
    }

    @PostMapping
    public TestObject addOrUpdateObject(@RequestBody TestObject testObject) {
        return testObjectComponent.add(testObject);
    }

    @DeleteMapping("/{id}")
    public TestObject deleteObject(@PathVariable int id) {
        TestObject object = testObjectComponent.removeById(id);
        if (object == null) {
            throw new RuntimeException("Object not found");
        }
        return object;
    }
}
