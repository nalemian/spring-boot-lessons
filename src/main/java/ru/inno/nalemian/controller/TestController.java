package ru.inno.nalemian.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.inno.nalemian.component.TestComponent;

@RestController
public class TestController {

    private final TestComponent testComponent;

    public TestController(TestComponent testComponent) {
        this.testComponent = testComponent;
    }

    @GetMapping
    public String test() {
        return testComponent.test();
    }
}
