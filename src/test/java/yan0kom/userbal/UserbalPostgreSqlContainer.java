package yan0kom.userbal;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

public class UserbalPostgreSqlContainer extends PostgreSQLContainer<UserbalPostgreSqlContainer> {
    private static final String IMAGE_VERSION = "postgres:14.7";
    private static UserbalPostgreSqlContainer container;

    private UserbalPostgreSqlContainer() {
        super(IMAGE_VERSION);
    }

    public static UserbalPostgreSqlContainer getInstance() {
        if (container == null) {
            container = new UserbalPostgreSqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());

        var containerDelegate = new JdbcDatabaseDelegate(container, "");
        ScriptUtils.runInitScript(containerDelegate, "schema.sql");
        ScriptUtils.runInitScript(containerDelegate, "data.sql");
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
