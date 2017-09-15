package rewards.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import rewards.domain.model.RewardRepository;
import rewards.infrastructure.jdbc.JdbcRewardRepository;

@Configuration
@Import({ ApplicationConfig.class })
public class SystemTestConfig {

	@Bean
	public RewardRepository rewardRepository() {
		return new JdbcRewardRepository(dataSource());
	}

	// TODO 10: Provide bean definitions for JpaAccountRepository and JpaMerchantRepository.
	// Note that you will not need to define any dependencies

	// TODO 11: Define a LocalContainerEntityManagerFactoryBean with the name entityManagerFactory 
	// Be sure to set the dataSource and jpaVendorAdaptor properties appropriately. 

	// TODO 12: Define a JpaTransactionManager bean with the name transactionManager 
	// The @Bean method should accept a parameter of type EntityManagerFactory.
	// Use this parameter when instantiating the JpaTransactionManager.
	// Run the RewardsApplicationServiceTests, it should pass. 

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

	/**
	 * Adds a bean post processor that enables automatic
	 * persistence exception translation to classes marked
	 * with @Repository annotation.
	 *
	 * @return persistence exception translation
	 */
	@Bean
	public PersistenceExceptionTranslationPostProcessor
			persistenceExceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
