package rewards.infrastructure.jdbc;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import rewards.domain.model.Account;
import rewards.domain.model.AccountRepository;
import rewards.domain.model.Card;

@Repository
public class JdbcAccountRepository implements AccountRepository {

	private class AccountExtractor implements ResultSetExtractor<Account> {
		@Override
		public Account extractData(ResultSet rs) throws SQLException,
				DataAccessException {
			return mapAccount(rs);
		}
	}

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcAccountRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private static final String SQL_FINDBY_CARDNUMBER =
			"SELECT a.ID AS ID, a.NUMBER AS NUMBER, a.NAME AS NAME, a.TOTAL_POINTS AS TOTAL_POINTS,"
					+ " b.NUMBER AS CARD_NUMBER, b.ID AS CARD_ID"
					+ " FROM T_ACCOUNT a, T_ACCOUNT_CARD c"
					+ " LEFT OUTER JOIN T_ACCOUNT_CARD b ON a.ID = b.ACCOUNT_ID"
					+ " WHERE c.NUMBER = ? AND c.ACCOUNT_ID = a.ID";

	@Override
	public Account findByCardNumber(String cardNumber) {
		return jdbcTemplate.query(
				SQL_FINDBY_CARDNUMBER, new AccountExtractor(), cardNumber);
	}

	private Account mapAccount(ResultSet rs) throws SQLException {
		Account account = null;
		Set<Card> cards = new HashSet<>();
		while (rs.next()) {
			if (account == null) {
				account = new Account(rs.getString("NUMBER"), rs.getString("NAME"));
				// To support updates, unique ID must be restored
				/*
				Field idField = ReflectionUtils.findField(Account.class, "id");
				ReflectionUtils.makeAccessible(idField);
				ReflectionUtils.setField(
						idField, account, rs.getLong("ID"));
				*/
			}
			cards.add(mapCard(rs));
		}
		if (account != null) {
			// Use reflection API to restore Set<Card> inside Account
			Field cardsField = ReflectionUtils.findField(Account.class, "cards");
			ReflectionUtils.makeAccessible(cardsField);
			ReflectionUtils.setField(cardsField, account, cards);
		}
		if (account == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return account;
	}

	private Card mapCard(ResultSet rs) throws SQLException {
		Card card = new Card(rs.getString("CARD_NUMBER"));
		// Use reflection API to restore Set<Card> inside Account
		/*
		Field idField = ReflectionUtils.findField(Card.class, "id");
		ReflectionUtils.makeAccessible(idField);
		ReflectionUtils.setField(
				idField, card, rs.getLong("CARD_ID"));
		*/
		return card;
	}

	private static final String SQL_UPDATE_ACCOUNT =
			"UPDATE T_ACCOUNT a"
					+ " SET a.TOTAL_POINTS = ?"
					+ " WHERE a.NUMBER = ?";

	@Override
	public Account updateAccount(Account account) {
		int rowsUpdated = jdbcTemplate.update(SQL_UPDATE_ACCOUNT,
				account.getTotalPoints(), account.getNumber());
		if (rowsUpdated != 1) {
			throw new IncorrectResultSizeDataAccessException(
					1, rowsUpdated);
		}
		return account;
	}

}
