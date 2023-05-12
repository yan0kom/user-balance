package yan0kom.userbal.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EntityScan(basePackages = "yan0kom.userbal.dao.entity")
@EnableScheduling
public class ApplicationConfig {
}
