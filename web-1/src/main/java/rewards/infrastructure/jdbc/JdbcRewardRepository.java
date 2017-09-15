package rewards.infrastructure.jdbc;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import rewards.application.Purchase;
import rewards.application.RewardConfirmation;
import rewards.domain.model.Account;
import rewards.domain.model.RewardRepository;

@Repository
public class JdbcRewardRepository implements RewardRepository {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcRewardRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private static final String SQL_INSERT_REWARD =
			"INSERT INTO T_REWARD (confirmation_number, reward_points, reward_date, account_number, card_number, merchant_number, purchase_amount, purchase_date)"
					+ " VALUES (?, ?, TODAY, ?, ?, ?, ?, ?)";

	@Override
	public RewardConfirmation confirmReward(
			Purchase purchase, Account account, int pointsEarned) {
		String nextConfirmationNumber = nextConfirmationNumber();
		jdbcTemplate.update(SQL_INSERT_REWARD,
				nextConfirmationNumber,
				pointsEarned,
				account.getNumber(),
				purchase.getCardNumber(),
				purchase.getMerchantNumber(),
				purchase.getAmount().getNumber().numberValue(BigDecimal.class),
				new java.sql.Date(purchase.getDate().getTime()));
		return new RewardConfirmation(
				nextConfirmationNumber,
				account.getNumber(),
				purchase.getAmount(),
				pointsEarned,
				account.getTotalPoints());
	}

	private static final String SQL_NEXT_CONFIRMATION_NUMBER =
			"select next value for S_REWARD_CONFIRMATION_NUMBER from DUAL_REWARD_CONFIRMATION_NUMBER";

	private String nextConfirmationNumber() {
		return jdbcTemplate.queryForObject(
				SQL_NEXT_CONFIRMATION_NUMBER, String.class);
	}

}
