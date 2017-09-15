package rewards.infrastructure.jpa;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import rewards.domain.model.Account;

public class JpaAccountRepositoryTests {
	
	private JpaAccountRepository repository;
	
	private EntityManagerFactory entityManagerFactory;
	
	private EntityManager entityManager;
	
	@Before
	public void setupAccountRepository() {
		entityManagerFactory = createEntityManagerFactory();
		entityManager = entityManagerFactory.createEntityManager();
		
		repository = new JpaAccountRepository();
		repository.setEntityManager(entityManager);
	}

	@After
	public void shutdownAccountRepository() {
		if (entityManager != null) {
			entityManager.close();
		}
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}
	
	@Test
	public void testFindAccountByCard() throws Exception {
		Account account = repository.findByCardNumber("1234123412341234");
		assertNotNull(account);
		assertEquals("1234567890", account.getNumber());
		assertEquals("Juan Dela Cruz", account.getName());
		assertEquals(1, account.getCards().size());
		assertEquals(0, account.getTotalPoints());
	}

	@Test(expected=NoResultException.class)
	public void throwsExceptionWhenCardNumberNotFound() throws Exception {
		repository.findByCardNumber("NON-EXISTENT CARD");
	}

	/**
	 * We are not using Spring in this unit test, so we have to setup our
	 * entity manager factory manually.
	 * 
	 * @return The entity manager factory.
	 */
	private EntityManagerFactory createEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(createTestDataSource());

		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.HSQL);
		jpaVendorAdapter.setShowSql(true);

		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

		Map<String, String> jpaProperties = new HashMap<String, String>();
		jpaProperties.put("hibernate.format_sql", "true");
		entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);
		
		entityManagerFactoryBean.setPackagesToScan("rewards.domain.model");

		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean.getObject();
	}
	
	/**
	 * We are not using Spring in this unit test, so we have to setup our
	 * embedded database manually.
	 * 
	 * @return The data source.
	 */
	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder()
			.setName("rewards")
			.addScript("classpath:rewards/schema.sql")
			.addScript("classpath:rewards/test-data.sql")
			.build();
	}

}
