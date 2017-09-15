package rewards.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rewards.application.RewardsApplicationService;
import rewards.application.RewardsApplicationServiceImpl;
import rewards.domain.model.AccountRepository;
import rewards.domain.model.MerchantRepository;
import rewards.domain.model.RewardRepository;
import rewards.infrastructure.jdbc.JdbcAccountRepository;
import rewards.infrastructure.jdbc.JdbcMerchantRepository;
import rewards.infrastructure.jdbc.JdbcRewardRepository;

@Configuration
public class ApplicationConfig {

	@Autowired
	DataSource dataSource;
	
	@Bean
	public RewardsApplicationService rewardsApplicationService() {
		return new RewardsApplicationServiceImpl(accountRepository(), merchantRepository(), rewardRepository());
	}
	
	@Bean
	public AccountRepository accountRepository() {
		return new JdbcAccountRepository(dataSource);
	}
	
	@Bean
	public MerchantRepository merchantRepository() {
		return new JdbcMerchantRepository(dataSource);
	}
	
	@Bean
	public RewardRepository rewardRepository() {
		return new JdbcRewardRepository(dataSource);
	}
}
