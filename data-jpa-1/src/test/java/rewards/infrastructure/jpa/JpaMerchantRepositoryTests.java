package rewards.infrastructure.jpa;

import static org.junit.Assert.*;

import java.math.BigDecimal;
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

import rewards.domain.model.Merchant;

public class JpaMerchantRepositoryTests {

	private JpaMerchantRepository repository;
	
	private EntityManagerFactory entityManagerFactory;
	
	private EntityManager entityManager;
	
	@Before
	public void setupMerchantRepository() {
		entityManagerFactory = createEntityManagerFactory();
		entityManager = entityManagerFactory.createEntityManager();
		
		repository = new JpaMerchantRepository();
		repository.setEntityManager(entityManager);
	}

	@After
	public void shutdownMerchantRepository() {
		if (entityManager != null) {
			entityManager.close();
		}
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}
	
	@Test
	public void findByNumber() throws Exception {
		Merchant merchant = repository.findByNumber("1115558888");
		assertNotNull(merchant);
		assertEquals("Acme Supplies", merchant.getName());
		assertEquals("1115558888", merchant.getNumber());
		assertEquals(new BigDecimal("50.0"),
				merchant.getAmountPerPoint().setScale(1));
		assertEquals(new BigDecimal("500.0"),
				merchant.getMinimumAmount().setScale(1));
	}
	
	@Test(expected=NoResultException.class)
	public void throwsExceptionWhenCardNumberNotFound() throws Exception {
		repository.findByNumber("NON-EXISTENT CARD");
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
			.addScript("/rewards/testdb/schema.sql")
			.addScript("/rewards/testdb/test-data.sql")
			.build();
	}

}
