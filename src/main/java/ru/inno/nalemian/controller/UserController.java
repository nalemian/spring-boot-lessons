package ru.inno.nalemian.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.inno.nalemian.model.Account;
import ru.inno.nalemian.model.User;
import ru.inno.nalemian.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/{id}")
    public Account addAccount(@PathVariable Long id, @RequestParam Long accountNumber, @RequestParam Double balance) {
        return userService.addAccount(id, accountNumber, balance);
    }

    @GetMapping("/{id}")
    public User getUserWithAccounts(@PathVariable Long id) {
        return userService.getUserWithAccounts(id);
    }
}
