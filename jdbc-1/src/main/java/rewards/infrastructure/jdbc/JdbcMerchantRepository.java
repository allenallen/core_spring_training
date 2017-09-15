package rewards.infrastructure.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import rewards.domain.model.Merchant;
import rewards.domain.model.MerchantRepository;

// : Add a field of type JdbcTemplate. Refactor the constructor to instantiate it.
// Refactor findByNumber(..) to use the JdbcTemplate and a RowMapper called MerchantMapper. 
// Note that the mapMerchant() method contains logic which the RowMapper may wish to use.
// When complete, save all changes and run JdbcMerchantRepositoryTests.  It should pass.

@Repository
public class JdbcMerchantRepository implements MerchantRepository {

	private final DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcMerchantRepository(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	private static final String SQL_FINDBY_NUMBER = "SELECT m.ID AS ID, m.NUMBER AS NUMBER, m.NAME AS NAME, m.AMOUNT_PER_POINT AS AMOUNT_PER_POINT, m.MINIMUM_PURCHASE_AMOUNT AS MINIMUM_PURCHASE_AMOUNT"
			+ " FROM T_MERCHANT m" + " WHERE m.NUMBER = ?";

	@Override
	public Merchant findByNumber(String merchantNumber) {
		// return jdbcTemplate.queryForObject(SQL_FINDBY_NUMBER, new
		// MerchantRowMapper(), merchantNumber);
		return jdbcTemplate.queryForObject(SQL_FINDBY_NUMBER, (rs, rowNum) -> {
			return mapMerchant(rs);
		}, merchantNumber);

	}

	private class MerchantRowMapper implements RowMapper<Merchant> {
		@Override
		public Merchant mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapMerchant(rs);
		}
	}

	private Merchant mapMerchant(ResultSet rs) throws SQLException {
		Merchant merchant = new Merchant(rs.getString("NUMBER"), rs.getString("NAME"));
		merchant.setAmountPerPoint(rs.getBigDecimal("AMOUNT_PER_POINT"));
		merchant.setMinimumAmount(rs.getBigDecimal("MINIMUM_PURCHASE_AMOUNT"));
		// To support updates, unique ID must be restored
		/*
		 * Field idField = ReflectionUtils.findField(Merchant.class, "id");
		 * ReflectionUtils.makeAccessible(idField); ReflectionUtils.setField( idField,
		 * merchant, rs.getLong("ID"));
		 */
		return merchant;
	}

}
