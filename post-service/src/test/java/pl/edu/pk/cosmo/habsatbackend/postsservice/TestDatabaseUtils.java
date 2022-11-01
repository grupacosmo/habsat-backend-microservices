package pl.edu.pk.cosmo.habsatbackend.postsservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TestDatabaseUtils {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void truncate() {
        entityManager.createNativeQuery("set session_replication_role = 'replica'").executeUpdate();
        List<String> tables = entityManager.getMetamodel().getManagedTypes().stream()
                .filter(it -> it.getJavaType().isAnnotationPresent(Table.class))
                .map(it -> it.getJavaType().getAnnotation(Table.class).name())
                .collect(Collectors.toList());
        tables.forEach(name -> entityManager.createNativeQuery("truncate table " + name + " cascade").executeUpdate());
        entityManager.createNativeQuery("set session_replication_role = 'origin'").executeUpdate();
    }
}