package pl.mikbac.examples.example100.employeeCRUD;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by MikBac on 03.01.2026
 */

@SpringBootTest(properties = {
        "logging.level.org.springframework.r2dbc=DEBUG" // Enable R2DBC SQL query logging
})
public abstract class AbstractTest {
}
