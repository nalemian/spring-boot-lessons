package ru.inno.nalemian.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.nalemian.dto.AccountDTO;
import ru.inno.nalemian.dto.UserDTO;
import ru.inno.nalemian.repository.AccountRepository;
import ru.inno.nalemian.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = ru.inno.nalemian.SpringBootLessonsApplication.class,
        webEnvironment = RANDOM_PORT
)
@Import(TestcontainersConfiguration.class)
class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void checkRepos() {
        accountRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @Order(0)
    void userService_shouldCreateUserAndAccount() {
        AccountDTO accountDTO = new AccountDTO(null, "12CompoCe", 100.0);
        UserDTO userDTO = new UserDTO(null, "name", List.of(accountDTO));
        UserDTO response = userService.createUser(userDTO);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getFullName()).isEqualTo(userDTO.getFullName());
        List<AccountDTO> accounts = response.getAccounts();
        assertThat(accounts).isNotNull().hasSize(1);
        assertThat(accounts.get(0).getAccountNumber()).isEqualTo("12CompoCe");
        assertThat(accounts.get(0).getBalance()).isEqualTo(100.0);
        assertThat(userRepository.findAll()).hasSize(1);
    }

    @Test
    @Order(1)
    void userController_shouldCreateUserAndAccount() {
        AccountDTO accountDTO = new AccountDTO(null, "12CompoCe", 100.0);
        UserDTO userDTO = new UserDTO(null, "name", List.of(accountDTO));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);
        UserDTO response = restTemplate.postForObject("/users", request, UserDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getFullName()).isEqualTo(userDTO.getFullName());
        List<AccountDTO> accounts = response.getAccounts();
        assertThat(accounts).isNotNull().hasSize(1);
        assertThat(accounts.get(0).getAccountNumber()).isEqualTo("12CompoCe");
        assertThat(accounts.get(0).getBalance()).isEqualTo(100.0);
        assertThat(userRepository.findAll()).hasSize(1);
    }
}
