package rewards.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "rewards.application", "rewards.infrastructure.jdbc" })
public class ApplicationConfig {

}
