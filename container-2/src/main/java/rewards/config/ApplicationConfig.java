package rewards.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import rewards.application.RewardsApplicationService;
import rewards.application.RewardsApplicationServiceImpl;
import rewards.domain.model.AccountRepository;
import rewards.domain.model.MerchantRepository;
import rewards.domain.model.RewardRepository;
import rewards.infrastructure.jdbc.JdbcAccountRepository;
import rewards.infrastructure.jdbc.JdbcMerchantRepository;
import rewards.infrastructure.jdbc.JdbcRewardRepository;

/* : Add the annotation to cause component scanning.
 * Set the base package to pick up all of the classes we have annotated so far.
 * Save all changes, Re-run the RewardsApplicationServiceTests.
 * It should now pass.
 */
@Configuration
@ComponentScan("rewards.infrastructure.jdbc")
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
		JdbcAccountRepository accountRepository = new JdbcAccountRepository();
		//accountRepository.setDataSource(dataSource);
		return accountRepository;
	}

	@Bean
	public MerchantRepository merchantRepository() {
		//return new JdbcMerchantRepository(dataSource);
		JdbcMerchantRepository merchantRepository = new JdbcMerchantRepository();
		merchantRepository.setDataSource(dataSource);
		return merchantRepository;
	}

	@Bean
	public RewardRepository rewardRepository() {
		JdbcRewardRepository rewardRepository = new JdbcRewardRepository();
		//rewardRepository.setDataSource(dataSource);
		return rewardRepository;
	}

	// Try executing the RewardsApplicationServiceTests. It should fail. Why?
	// We will fix this in the next steps.
	//Beans are not found

}
