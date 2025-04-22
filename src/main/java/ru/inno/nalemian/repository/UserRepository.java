package ru.inno.nalemian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inno.nalemian.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
