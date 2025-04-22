package ru.inno.nalemian.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.inno.nalemian.model.Account;
import ru.inno.nalemian.model.User;
import ru.inno.nalemian.repository.AccountRepository;
import ru.inno.nalemian.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public User createUser(String fullName, Long accountNumber, Double balance) {
        User user = new User();
        user.setFullName(fullName);
        user = userRepository.save(user);
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(balance);
        account.setUser(user);
        accountRepository.save(account);
        user.setAccounts(List.of(account));
        return user;
    }

    public Account addAccount(Long id, Long accountNumber, Double balance) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new RuntimeException("User not found");
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(balance);
        account.setUser(user);
        return accountRepository.save(account);
    }

    public User getUserWithAccounts(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new RuntimeException("User not found");
        return user;
    }
}
