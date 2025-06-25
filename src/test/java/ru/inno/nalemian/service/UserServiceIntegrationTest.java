package ru.inno.nalemian.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.inno.nalemian.dto.AccountDTO;
import ru.inno.nalemian.dto.UserDTO;
import ru.inno.nalemian.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = ru.inno.nalemian.SpringBootLessonsApplication.class,
        webEnvironment = RANDOM_PORT
)
@Import(TestcontainersConfiguration.class)
class UserServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAllInBatch();
    }

    @Test
    void shouldCreateUserAndAccount() {
        AccountDTO accountDTO = new AccountDTO(null, 12L, 100.0);
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
        assertThat(accounts.get(0).getAccountNumber()).isEqualTo(12L);
        assertThat(accounts.get(0).getBalance()).isEqualTo(100.0);
    }
}
