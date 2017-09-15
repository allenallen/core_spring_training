package rewards.application;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import rewards.config.SystemTestConfig;

@ContextConfiguration(classes=SystemTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RewardsApplicationServicePropagationTests {

	@Autowired
	private RewardsApplicationService rewardsService;

	private String merchantNumber;
	private String cardNumber;

	@Autowired
	private PlatformTransactionManager transactionManager;
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() throws Exception {
		merchantNumber = "1115558888";
		cardNumber = "1234123412341234";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Autowired
	public void initJdbcTemplate(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// TODO 04a: This test passes when the #rewardAccountFor() is @Transactional and has propagation REQUIRED (default)
	@Test
	public void testPropagation() throws Exception {
		int originalCount = (int) jdbcTemplate.queryForObject(
				"SELECT count(*) FROM t_reward", Integer.class);

		// Open a transaction for testing
		TransactionStatus status = transactionManager.getTransaction(
				new DefaultTransactionDefinition());
		doRewardForPurchase();
		transactionManager.rollback(status);

		// TODO 04b: The assertion below fails when #rewardAccountFor() uses a NEW transaction via Propagation.REQUIRES_NEW
		// Try changing #rewardAccountFor() to Propagation.REQUIRES_NEW
		// and see this test fail. Why?

		// Check that rewards were not inserted
		assertEquals(originalCount, (int) jdbcTemplate.queryForObject(
				"SELECT count(*) FROM t_reward", Integer.class));
	}

	private MonetaryAmount monetaryAmountOf(String amount) {
		return Monetary.getDefaultAmountFactory()
				.setNumber(new BigDecimal(amount))
				.setCurrency("USD")
				.create();
	}

	private Purchase purchaseOf(String amount) {
		return new Purchase(
				monetaryAmountOf(amount),
				merchantNumber, cardNumber);
	}

	private void doRewardForPurchase() {
		rewardsService.rewardAccountFor(
				purchaseOf("500.00"));
	}

}
