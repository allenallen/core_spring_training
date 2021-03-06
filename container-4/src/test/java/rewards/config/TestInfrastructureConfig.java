package rewards.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
@Import(ApplicationConfig.class)
@PropertySource("classpath:rewards/testdb/testdb.properties")
public class TestInfrastructureConfig {

	@Value("${schemaLocation}")
	private String schemaLocation;
	@Value("${testDataLocation}")
	private String testDataLocation;

	@Bean
	public static PropertySourcesPlaceholderConfigurer pspc() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/**
	 * Creates an in-memory "rewards" database populated with test data for fast
	 * testing
	 */
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().addScript(schemaLocation).addScript(testDataLocation).build();
	}

}
