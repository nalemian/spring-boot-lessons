package ru.inno.nalemian;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.inno.nalemian.model.Document;

public class PrototypeScopeTest {
    @Test
    void testPrototypeScopeUsageViaDifferentValues() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("scopes.xml");
        Document document1 = (Document) applicationContext.getBean("documentPrototype");
        Document document2 = (Document) applicationContext.getBean("documentPrototype");
        Assertions.assertNotSame(document1, document2);
        document1.setValue("name1");
        document2.setValue("name2");
        Assertions.assertEquals("name1", document1.getValue());
        Assertions.assertEquals("name2", document2.getValue());
        Assertions.assertNotSame(document1.getValue(), document2.getValue());
        applicationContext.close();
    }
}
