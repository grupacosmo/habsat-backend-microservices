package pl.edu.pk.cosmo.habsatbackend.postsservice;

import lombok.extern.slf4j.Slf4j;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class E2ETest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestDatabaseUtils testDatabaseUtils;
    @Autowired
    private DataSource dataSource;
    @Autowired
    protected MockMvc mvc;

    @AfterEach
    public void afterEach() {
        testDatabaseUtils.truncate();
    }

    public String api(String path) {
        path = path.startsWith("/") ? path.substring(1) : path;
        return "http://localhost:" + port + "/" + path;
    }

    public Table table(String table) {
        return new Table(dataSource, table);
    }
}
