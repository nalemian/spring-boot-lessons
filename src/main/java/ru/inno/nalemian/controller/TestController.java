package ru.inno.nalemian.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.inno.nalemian.component.TestComponent;
import ru.inno.nalemian.model.TestObject;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestComponent testComponent;
    private TestObject currentObject = TestObject.builder()
            .id(1)
            .name("testName")
            .build();

    //@RequestMapping применяется и к классам, и к методам,
    //нужно явно указывать HTTP-метод в параметре "method",
    //используется для любых HTTP-методов
    @RequestMapping(value = "/req-mapping", method = RequestMethod.GET)
    public String exampleGet() {
        return "example of GET with RequestMapping";
    }

    //Может поддерживать сразу несколько HTTP-методов
    @RequestMapping(value = "/multi", method = {RequestMethod.GET, RequestMethod.POST})
    public String multiMethod() {
        return "work with both GET and POST";
    }

    //Специализированные аннотации - для конкретных HTTP-методов

    //Get - запрос для получения данных (эквивалентно @RequestMapping(method = RequestMethod.GET))
    @GetMapping
    public String test() {
        return testComponent.test();
    }

    //Post - передача данных на сервер для их обработки, создание ресурса
    //PathVariable - позволяет извлекать значение из URL
    @PostMapping("/create/{name}")
    public TestObject postWithPath(@PathVariable String name) {
        return TestObject.builder()
                .id(2)
                .name(name)
                .build();
    }

    //При RequestParam нужно передавать параметры в самом запросе (например в Postman в Query Params)
    @PostMapping("/create")
    public TestObject postWithRequest(@RequestParam String name) {
        return TestObject.builder()
                .id(3)
                .name(name)
                .build();
    }

    //При RequestBody данные отправляются в теле запроса
    //JSON автоматически преобразуется в Java-объект
    @PostMapping("/create-with-body")
    public TestObject createWithRequestBody(@RequestBody TestObject newObject) {
        this.currentObject = newObject;
        return currentObject;
    }

    //Put - явное обновление существующих ресурсов или создание новых
    @PutMapping("/put")
    public String putExample(@RequestParam int id) {
        return "method PUT with PutMapping";
    }

    //DeleteMapping полностью удаляет данные нужного объекта
    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable int id) {
        if (currentObject.getId() == id) {
            currentObject = null;
            return "Object with id " + id + " deleted";
        }
        return "Object with id " + id + " not found";
    }

    //PatchMapping обновляет только указанные поля объекта
    @PatchMapping("/update-name")
    public TestObject updateName(@RequestParam String newName) {
        currentObject.setName(newName);
        return currentObject;
    }
}
