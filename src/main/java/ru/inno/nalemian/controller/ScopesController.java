package ru.inno.nalemian.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.inno.nalemian.model.Document;

@RestController
@RequestMapping("/scopes")
public class ScopesController {
    @Resource(name = "requestScopedBean")
    private Document requestScopedBean;
    @Resource(name = "sessionScopedBean")
    private Document sessionScopedBean;
    private int requestCount = 0;
    private int sessionCount = 0;

    @GetMapping("/request")
    public String getRequestScopeMessage() {
        System.out.println(requestScopedBean.getValue());
        requestCount++;
        requestScopedBean.setValue("Request number: " + requestCount);
        return requestScopedBean.getValue();
    }

    @GetMapping("/session")
    public String getSessiomScopeMessage() {
        System.out.println(sessionScopedBean.getValue());
        sessionCount++;
        sessionScopedBean.setValue("Request number: " + sessionCount);
        return sessionScopedBean.getValue();
    }
}
