package rewards.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

/*
 * : Plug in the aspect configuration. 
 * Save all work, run the LoggingAspectTests.
 * It should pass, and you should see logging output
 * in the console.	 
 */
@Configuration
@Import({ ApplicationConfig.class, AspectsConfig.class})
public class SystemTestConfig {

	/**
	 * Creates an in-memory "rewards" database populated 
	 * with test data for fast testing
	 */
	@Bean
	public DataSource dataSource() {
		return
			(new EmbeddedDatabaseBuilder())
			.addScript("classpath:rewards/testdb/schema.sql")
			.addScript("classpath:rewards/testdb/test-data.sql")
			.build();
	}	

}
