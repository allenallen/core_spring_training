package rewards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import rewards.application.LoggingBeanPostProcessor;

@Configuration
@Import({
	TestInfrastructureDevConfig.class,
	TestInfrastructureProductionConfig.class,
	ApplicationConfig.class })
public class TestInfrastructureConfig {

	@Bean
	public LoggingBeanPostProcessor loggingBean() {
		return new LoggingBeanPostProcessor();
	}

}
