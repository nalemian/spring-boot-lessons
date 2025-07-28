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
        webEnvironment = RANDOM_PORT,
        /**
         * нужно для fix_withBatchSize - при инициализации Hibernate видит,
         * что нужно будет работать с несколькими коллекциями, и в будущем
         * объединяет запросы в один
        */
        properties = "spring.jpa.properties.hibernate.default_batch_fetch_size=32"
)
@Import(TestcontainersConfiguration.class)
@Transactional
public class NPlusOneFixesTest {
    @Autowired
    ParentRepository parents;
    private Statistics stats;
    @Autowired
    EntityManagerFactory emf;
    @Autowired
    EntityManager em;

    // заполнение таблиц перед каждым тестом
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
        em.flush(); // для записи в БД
        em.clear();
        stats.clear();
    }

    @Test
    void fix_withJoinFetch() {
        List<Parent> list = parents.findAllFetchJoin();
        list.forEach(p -> p.getChildren().size());
        Assertions.assertEquals(1, stats.getPrepareStatementCount());
    }

    @Test
    void fix_withEntityGraph() {
        List<Parent> list = parents.findAllWithGraph();
        list.forEach(p -> p.getChildren().size());
        Assertions.assertEquals(1, stats.getPrepareStatementCount());
    }

    @Test
    void fix_withBatchSize() {
        List<Parent> list = parents.findAllPlain();
        list.forEach(p -> p.getChildren().size());
        Assertions.assertEquals(2, stats.getPrepareStatementCount());
    }
}
