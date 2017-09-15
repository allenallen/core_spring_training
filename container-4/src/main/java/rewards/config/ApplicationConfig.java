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
	public RewardsApplicationService rewardsService() {
		return new RewardsApplicationServiceImpl(
			accountRepository(), 
			merchantRepository(), 
			rewardRepository());
	}

	@Bean
	public AccountRepository accountRepository() {
		JdbcAccountRepository repository = new JdbcAccountRepository();
		repository.setDataSource(dataSource);
		return repository;
	}

	@Bean
	public MerchantRepository merchantRepository() {
		JdbcMerchantRepository repository = new JdbcMerchantRepository();
		repository.setDataSource(dataSource);
		return repository;
	}

	@Bean
	public RewardRepository rewardRepository() {
		JdbcRewardRepository repository = new JdbcRewardRepository();
		repository.setDataSource(dataSource);
		return repository;
	}
	
}
