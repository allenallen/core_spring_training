package rewards.config;

import javax.sql.DataSource;

import org.hsqldb.TransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import({ ApplicationConfig.class })
public class SystemTestConfig {

	// TODO 02: Define a bean named "transactionManager" that configures a
	// DataSourceTransactionManager
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());

	}

	/**
	 * Creates an in-memory "rewards" database populated with test data for fast
	 * testing
	 */
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().addScript("classpath:rewards/testdb/schema.sql")
				.addScript("classpath:rewards/testdb/test-data.sql").build();
	}

}
