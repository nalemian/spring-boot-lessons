package ru.inno.nalemian.problemDemo.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ru.inno.nalemian.problemDemo.model.Parent;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    // простой запрос для демонстрации n+1
    @Query("select p from Parent p")
    List<Parent> findAllPlain();

    // left join fetch собирает детей за один sql-запрос
    @Query("""
                select distinct p
                from Parent p
                left join fetch p.children
            """)
    List<Parent> findAllFetchJoin();

    // то же самое, но через аннотацию
    @EntityGraph(attributePaths = "children")
    @Query("select p from Parent p")
    List<Parent> findAllWithGraph();
}