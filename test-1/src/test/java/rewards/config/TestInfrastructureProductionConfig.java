package rewards.config;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import rewards.application.SimpleJndiHelper;

@Configuration
@Profile("jdbc-production")
public class TestInfrastructureProductionConfig {

	@Bean
	public static SimpleJndiHelper jndiHelper() {
		return new SimpleJndiHelper();
	}
	
	@Bean
	public DataSource dataSource() throws Exception {
		return (DataSource) 
			(new InitialContext())
				.lookup("java:/comp/env/jdbc/rewards");	
	}

}
