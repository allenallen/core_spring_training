package rewards.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// TODO 03: Add an annotation to instruct the container to look for @Transactional annotations
// Save all you work, run RewardsApplicationServiceTests. It should still pass.  
// Inspect your console output to see if the same connection is being used.

@Configuration
@ComponentScan({ "rewards.application", "rewards.infrastructure.jdbc" })
public class ApplicationConfig {

}
