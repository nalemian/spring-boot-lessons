package ru.inno.nalemian.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.inno.nalemian.dto.AccountDTO;
import ru.inno.nalemian.dto.UserDTO;
import ru.inno.nalemian.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO UserDTO) {
        return userService.createUser(UserDTO);
    }

    @PostMapping("/{id}")
    public AccountDTO addAccount(@PathVariable Long id, @RequestParam String accountNumber, @RequestParam Double balance) {
        return userService.addAccount(id, accountNumber, balance);
    }

    @GetMapping("/{id}")
    public UserDTO getUserWithAccounts(@PathVariable Long id) {
        return userService.getUserWithAccounts(id);
    }
}
