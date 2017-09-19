package rewards.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import rewards.domain.model.AccountRepository;
import rewards.domain.model.MerchantRepository;
import rewards.domain.model.RewardRepository;
import rewards.infrastructure.jdbc.JdbcRewardRepository;
import rewards.infrastructure.jpa.JpaAccountRepository;
import rewards.infrastructure.jpa.JpaMerchantRepository;

// TODO 03a: Enable JPA repository implementation generation
@Configuration
@Import({ ApplicationConfig.class })
public class SystemTestConfig {

	// Now that we've enabled JPA repository generation,
	// do we still need to explicitly instantiate JPA
	// repository beans?
	// TODO 03b: Remove unnecessary JPA repository beans

	@Bean
	public RewardRepository rewardRepository() {
		return new JdbcRewardRepository(dataSource());
	}

	@Bean
	public AccountRepository accountRepository() {
		return new JpaAccountRepository();
	}

	@Bean
	public MerchantRepository merchantRepository() {
		return new JpaMerchantRepository();
	}

	// TODO 04: Run RewardsApplicationServiceTests again.
	// When it passes, congratulations! You're now using Spring Data JPA.
	// It's a good thing we separated the configuration of
	// infrastructure beans.

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabase(Database.HSQL);

		Properties props = new Properties();
		props.setProperty("hibernate.format_sql", "true");
		
		LocalContainerEntityManagerFactoryBean emfb = 
			new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource());
		emfb.setPackagesToScan("rewards.domain.model");
		emfb.setJpaProperties(props);
		emfb.setJpaVendorAdapter(adapter);
		
		return emfb;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	/**
	 * Creates an in-memory "rewards" database populated 
	 * with test data for fast testing
	 */
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
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
