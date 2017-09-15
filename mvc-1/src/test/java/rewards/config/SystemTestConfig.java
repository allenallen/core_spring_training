package rewards.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@Import({ ApplicationConfig.class, InfrastructureConfig.class })
public class SystemTestConfig {

	@Autowired
	private DataSource dataSource;

	/*
	 * Override bean definition to show SQL for debugging purposes.
	 */
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
		emfb.setDataSource(dataSource);
		emfb.setPackagesToScan("rewards.domain.model");
		emfb.setJpaProperties(props);
		emfb.setJpaVendorAdapter(adapter);
		
		return emfb;
	}

}
