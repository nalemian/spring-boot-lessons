package ru.inno.nalemian.problemDemo;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.nalemian.service.TestcontainersConfiguration;
import ru.inno.nalemian.problemDemo.model.*;
import ru.inno.nalemian.problemDemo.repository.ParentRepository;
import org.hibernate.stat.Statistics;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = ru.inno.nalemian.SpringBootLessonsApplication.class,
        webEnvironment = RANDOM_PORT
)
@Import(TestcontainersConfiguration.class)
@Transactional
public class NPlusOneTest {
    @Autowired
    ParentRepository parents;
    private Statistics stats;
    @Autowired
    EntityManagerFactory emf;
    @Autowired
    EntityManager em;

    @BeforeEach
    void seed() {
        stats = emf.unwrap(org.hibernate.SessionFactory.class).getStatistics();
        stats.setStatisticsEnabled(true);
        for (int i = 1; i <= 5; i++) {
            Parent p = new Parent("parent" + i);
            for (int j = 1; j <= 3; j++) {
                p.getChildren().add(new Child("child" + j, p));
            }
            parents.save(p);
        }
        em.flush();
        em.clear();
        stats.clear();
    }

    @Test
    void baseline_nPlusOne() {
        List<Parent> list = parents.findAllPlain();
        list.forEach(p -> p.getChildren().size());
        // 1 запрос за получение родителей и по одному на каждого родителя на получение детей
        Assertions.assertEquals(6, stats.getPrepareStatementCount());
    }
}
