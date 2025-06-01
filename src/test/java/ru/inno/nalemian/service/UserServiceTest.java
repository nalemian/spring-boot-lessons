package ru.inno.nalemian.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.inno.nalemian.dto.AccountDTO;
import ru.inno.nalemian.dto.UserDTO;
import ru.inno.nalemian.model.Account;
import ru.inno.nalemian.model.User;
import ru.inno.nalemian.repository.AccountRepository;
import ru.inno.nalemian.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Юнит тест для класса userService - проверяет работоспобосбность отдельного класса UserService
 */
@ExtendWith(MockitoExtension.class)
        // запускает всякую автоматику связанную с mockito
class UserServiceTest {

    @Mock // сюда mockito вставит заглушку вместо реального класса
    private UserRepository userRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks // соберет реальный класс используя известные заглушки
    private UserService userService;

    /**
     * негативный сценарий, проверяющий строку кода с обработкой ошибок
     */
    @Test
    void createUser_whenAccountsAreNull_thenThrowsRuntime() {
        assertThrows(RuntimeException.class, () -> userService.createUser(UserDTO.builder().build()));
    }

    /**
     * негативный сценарий, проверяющий строку кода с обработкой ошибок
     */
    @Test
    void createUser_whenAccountsAreEmpty_thenThrowsRuntime() {
        assertThrows(RuntimeException.class, () -> userService.createUser(UserDTO.builder().accounts(List.of())
                .build()));
    }

    /**
     * Просто позитивный сценарий
     */
    @Test
    void createUser() {

        // arrange подготавливаем все для вызова
        // применяю разные варианты мока ответов. в данном случае уже не прямая заглушка, а отрабатываем от входного параметра
        when(userRepository.save(any()))
                .thenAnswer(
                        invocationOnMock -> {
                            User user = invocationOnMock.getArgument(0);
                            user.setId(1L); // обычно jpa save на новой сущности присваивает ей id
                            return user;
                        }
                );

        // а тут вне зависимости от того что пришло просто возвращаю заглушку ответа. но при этом делаю перехват значений
        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        when(accountRepository.save(captor.capture()))
                .thenReturn(mock(Account.class));

        AccountDTO accountInput = AccountDTO.builder()
                .accountNumber(123L)
                .balance(1d)
                .build();
        UserDTO input = UserDTO.builder()
                .fullName("test")
                .accounts(List.of(accountInput))
                .build();

        // act делаем вызов
        var result = userService.createUser(input);

        // assert проверяем что получилось

        assertNotNull(result.getId());
        assertEquals(input.getFullName(), result.getFullName());
        assertEquals(1, result.getAccounts().size());

        // достаем из captor-а значение на проверку
        var accountToSave = captor.getValue();
        assertEquals(accountInput.getAccountNumber(), accountToSave.getAccountNumber());
        assertEquals(accountInput.getBalance(), accountToSave.getBalance());
        assertNull(accountInput.getId());

        // проверяем результат. обрати внимание, из save возвращается мок, поэотому поля ответа пустые
        var accountResult = result.getAccounts().get(0);
        assertNull(accountResult.getId());
        assertNull(accountResult.getAccountNumber());
        assertNull(accountResult.getBalance());
    }
}