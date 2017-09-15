package rewards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import rewards.infrastructure.monitor.MonitorFactory;
import rewards.infrastructure.monitor.jamon.JamonMonitorFactory;

// : Add a class-level annotation to scan for components
// located in the rewards.infrastructure.monitor package.

// : Add a class-level annotation to process the @Aspect annotation.
@Configuration
@ComponentScan("rewards.infrastructure.monitor")
@EnableAspectJAutoProxy
public class AspectsConfig {

	@Bean
	public MonitorFactory monitorFactory() {
		return new JamonMonitorFactory();
	}

}
