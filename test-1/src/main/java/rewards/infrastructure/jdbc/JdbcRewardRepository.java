package rewards.infrastructure.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import rewards.application.Purchase;
import rewards.application.RewardConfirmation;
import rewards.domain.model.Account;
import rewards.domain.model.RewardRepository;

@Repository
@Profile("jdbc")
public class JdbcRewardRepository implements RewardRepository {

	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private static final String SQL_INSERT_REWARD =
			"INSERT INTO T_REWARD (confirmation_number, reward_points, reward_date, account_number, card_number, merchant_number, purchase_amount, purchase_date)"
					+ " VALUES (?, ?, TODAY, ?, ?, ?, ?, ?)";

	@Override
	public RewardConfirmation confirmReward(
			Purchase purchase, Account account, int pointsEarned) {
		try {
			Connection conn = dataSource.getConnection();
			try {
				PreparedStatement ps = conn.prepareStatement(SQL_INSERT_REWARD);
				try {
					String nextConfirmationNumber = nextConfirmationNumber();
					ps.setString(1, nextConfirmationNumber);
					ps.setInt(2, pointsEarned);
					ps.setString(3, account.getNumber());
					ps.setString(4, purchase.getCardNumber());
					ps.setString(5, purchase.getMerchantNumber());
					ps.setBigDecimal(6, purchase.getAmount().getNumber().numberValue(BigDecimal.class));
					ps.setDate(7, new java.sql.Date(purchase.getDate().getTime()));
					ps.execute();
					return new RewardConfirmation(
							nextConfirmationNumber,
							account.getNumber(),
							purchase.getAmount(),
							pointsEarned,
							account.getTotalPoints());
				} finally {
					ps.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			// Interface defines no throws clause,
			// but SQLException is not a RuntimeException.
			// So, we wrap it, and comply with the interface.
			throw new RuntimeException(
					"SQL exception inserting reward", e);
		}
	}

	private static final String SQL_NEXT_CONFIRMATION_NUMBER =
			"select next value for S_REWARD_CONFIRMATION_NUMBER from DUAL_REWARD_CONFIRMATION_NUMBER";

	private String nextConfirmationNumber() {
		try {
			Connection conn = dataSource.getConnection();
			try {
				PreparedStatement ps = conn.prepareStatement(SQL_NEXT_CONFIRMATION_NUMBER);
				ResultSet rs = ps.executeQuery();
				try {
					rs.next();
					return rs.getString(1);
				} finally {
					rs.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			// Interface defines no throws clause,
			// but SQLException is not a RuntimeException.
			// So, we wrap it, and comply with the interface.
			throw new RuntimeException(
					"SQL exception getting next confirmation number", e);
		}
	}

}
