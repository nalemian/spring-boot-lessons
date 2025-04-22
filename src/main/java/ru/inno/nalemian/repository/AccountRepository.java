package ru.inno.nalemian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inno.nalemian.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
