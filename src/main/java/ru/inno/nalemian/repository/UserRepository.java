package ru.inno.nalemian.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.nalemian.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    <S extends User> S save(@Nonnull S entity);
}
