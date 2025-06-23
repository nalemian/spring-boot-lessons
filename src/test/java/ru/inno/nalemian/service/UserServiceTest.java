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
import java.util.Optional;

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

        Account mockedAccount = mock(Account.class);
        when(mockedAccount.getId()).thenReturn(null);
        when(mockedAccount.getAccountNumber()).thenReturn(null);
        when(mockedAccount.getBalance()).thenReturn(null);
        // а тут вне зависимости от того что пришло просто возвращаю заглушку ответа. но при этом делаю перехват значений
        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        when(accountRepository.save(captor.capture()))
                .thenReturn(mockedAccount);

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

        // проверяем результат. обрати внимание, из save возвращается мок, поэтому поля ответа пустые
        var accountResult = result.getAccounts().get(0);
        assertNull(accountResult.getId());
        assertNull(accountResult.getAccountNumber());
        assertNull(accountResult.getBalance());
    }

    @Test
    void addAccount_whenDifferentArguments_thenDifferentAnswers() {
        User user = User.builder()
                .id(1l)
                .fullName("name")
                .build();
        when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        Account mockedAccount1 = mock(Account.class);
        when(mockedAccount1.getId()).thenReturn(1L);
        when(mockedAccount1.getAccountNumber()).thenReturn(10L);
        when(mockedAccount1.getBalance()).thenReturn(10d);
        Account mockedAccount2 = mock(Account.class);
        when(mockedAccount2.getId()).thenReturn(2L);
        when(mockedAccount2.getAccountNumber()).thenReturn(20L);
        when(mockedAccount2.getBalance()).thenReturn(20d);
        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        when(accountRepository.save(captor.capture()))
                .thenAnswer(invocation -> {
                    Account passed = invocation.getArgument(0);
                    Long accountNumber = passed.getAccountNumber();
                    if (accountNumber != null && accountNumber.equals(10L)) {
                        return mockedAccount1;
                    } else if (accountNumber != null && accountNumber.equals(20L)) {
                        return mockedAccount2;
                    }
                    return null;
                });
        AccountDTO result1 = userService.addAccount(1L, 10L, 10d);
        assertEquals(1L, result1.getId());
        assertEquals(10L, result1.getAccountNumber());
        assertEquals(10d, result1.getBalance());
        AccountDTO result2 = userService.addAccount(1L, 20L, 20d);
        assertEquals(2L, result2.getId());
        assertEquals(20L, result2.getAccountNumber());
        assertEquals(20d, result2.getBalance());
    }

    @Test
    void addAccount_whenUserNotFound_thenThrowsRuntimeException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.addAccount(1L, 10L, 10d));
    }

    @Test
    void addAccount_whenSavingAccountThrowsException_thenThrowsRuntimeException() {
        User user = User.builder()
                .id(1L)
                .fullName("name")
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountRepository.save(any())).thenThrow(new RuntimeException("Some error"));
        assertThrows(RuntimeException.class, () -> userService.addAccount(1L, 10L, 10d));
    }

    @Test
    void addAccount() {
        User user = User.builder()
                .id(1L)
                .fullName("name")
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Account mockedAccount = mock(Account.class);
        when(mockedAccount.getId()).thenReturn(1L);
        when(mockedAccount.getAccountNumber()).thenReturn(10L);
        when(mockedAccount.getBalance()).thenReturn(10d);
        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        when(accountRepository.save(captor.capture())).thenReturn(mockedAccount);
        AccountDTO result = userService.addAccount(1L, 10L, 10d);
        System.out.println("Created account: " + result);
        assertEquals(1L, result.getId());
        assertEquals(10L, result.getAccountNumber());
        assertEquals(10d, result.getBalance());
    }

    @Test
    void getUserWithAccounts_whenUserNotFound_thenThrowsRuntimeException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserWithAccounts(1L));
    }

    @Test
    void getUserWithAccounts_whenNoAccounts_thenReturnsEmptyAccountList() {
        User user = User.builder()
                .id(1L).fullName("name")
                .accounts(List.of())
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDTO result = userService.getUserWithAccounts(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("name", result.getFullName());
        assertTrue(result.getAccounts().isEmpty());
    }

    @Test
    void getUserWithAccounts() {
        User user = User.builder()
                .id(1L)
                .fullName("name")
                .build();
        Account account = Account.builder()
                .id(1L)
                .accountNumber(10L)
                .balance(100.0)
                .user(user)
                .build();
        user.setAccounts(List.of(account));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDTO result = userService.getUserWithAccounts(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("name", result.getFullName());
        assertEquals(1, result.getAccounts().size());
        assertEquals(10L, result.getAccounts().get(0).getAccountNumber());
        assertEquals(100.0, result.getAccounts().get(0).getBalance());
    }
}