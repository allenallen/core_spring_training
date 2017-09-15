package rewards.infrastructure.jdbc;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Map;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import rewards.application.Purchase;
import rewards.application.RewardConfirmation;
import rewards.domain.model.Account;

public class JdbcRewardRepositoryTests {

	private JdbcRewardRepository rewardRepository;
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() throws Exception {
		dataSource = createTestDataSource();
		rewardRepository = new JdbcRewardRepository(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@After
	public void tearDown() throws Exception {
	}

	private MonetaryAmount monetaryAmountOf(String amount) {
		return Monetary.getDefaultAmountFactory()
				.setNumber(new BigDecimal(amount))
				.setCurrency("USD")
				.create();
	}

	@Test
	public void testCreateReward() throws Exception {
		String accountNumber = "1234567890";
		String cardNumber = "1234123412341234";
		String merchantNumber = "1115558888";
		Purchase purchase = new Purchase(
				monetaryAmountOf("50.00"), merchantNumber, cardNumber);
		Account account = new Account(accountNumber, "John Doe");
		int pointsEarned = 2;
		RewardConfirmation confirmation = rewardRepository.confirmReward(
				purchase, account, pointsEarned);
		assertNotNull(confirmation);
		verifyInsertedValues(confirmation.getConfirmationNumber());
	}

	private void verifyInsertedValues(String confirmationNumber) throws Exception {
		assertEquals(1, getRowCount());
		Map<String, Object> values = jdbcTemplate.queryForMap(
				"SELECT * FROM t_reward WHERE confirmation_number = ?", confirmationNumber);

		assertEquals("1234567890", values.get("ACCOUNT_NUMBER"));
		assertEquals("1234123412341234", values.get("CARD_NUMBER"));
		assertEquals("1115558888", values.get("MERCHANT_NUMBER"));
		assertEquals(2, values.get("REWARD_POINTS"));
	}

	private int getRowCount() {
		return jdbcTemplate.queryForObject(
				"SELECT count(*) FROM t_reward", Integer.class);
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder()
			.setName("rewards")
			.addScript("classpath:rewards/schema.sql")
			.addScript("classpath:rewards/test-data.sql")
			.build();
	}

}
