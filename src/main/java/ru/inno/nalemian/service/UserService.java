package ru.inno.nalemian.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.inno.nalemian.dto.UserDTO;
import ru.inno.nalemian.dto.AccountDTO;
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

    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO.getAccounts() == null || userDTO.getAccounts().isEmpty()) {
            throw new RuntimeException("User must have at least one account");
        }
        User user = User.builder()
                .fullName(userDTO.getFullName())
                .build();
        user = userRepository.save(user);
        AccountDTO accountDTO = userDTO.getAccounts().get(0);
        Account account = Account.builder()
                .accountNumber(accountDTO.getAccountNumber())
                .balance(accountDTO.getBalance())
                .user(user)
                .build();
        accountRepository.save(account);
        user.setAccounts(List.of(account));
        accountDTO.setId(account.getId());
        return new UserDTO(user.getId(), user.getFullName(), List.of(accountDTO));
    }

    public AccountDTO addAccount(Long id, Long accountNumber, Double balance) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new RuntimeException("User not found");
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .balance(balance)
                .user(user)
                .build();
        Account savedAccount = accountRepository.save(account);
        return new AccountDTO(savedAccount.getId(), savedAccount.getAccountNumber(), savedAccount.getBalance());
    }

    public UserDTO getUserWithAccounts(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new RuntimeException("User not found");
        List<AccountDTO> accounts = user.getAccounts().stream()
                .map(account -> new AccountDTO(account.getId(), account.getAccountNumber(), account.getBalance()))
                .toList();
        return new UserDTO(user.getId(), user.getFullName(), accounts);
    }
}
