package rewards.infrastructure.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import rewards.domain.model.Merchant;
import rewards.domain.model.MerchantRepository;

@Repository
@Profile("jdbc")
public class JdbcMerchantRepository implements MerchantRepository {

	private DataSource dataSource;

	private final Map<String, Merchant> merchantCache = new HashMap<>();
	private long merchantCacheHits = 0;

	public JdbcMerchantRepository(DataSource dataSource) {
		this.dataSource = dataSource;
		this.populateCache();
	}

	public JdbcMerchantRepository() {}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Merchant findByNumber(String merchantNumber) {
		Merchant merchant = merchantCache.get(merchantNumber);
		if (merchant == null) {
			throw new EmptyResultDataAccessException(1);
		}
		merchantCacheHits++;
		return merchant;
	}

	private static final String SQL_FIND_ALL = "SELECT NAME, NUMBER, AMOUNT_PER_POINT, MINIMUM_PURCHASE_AMOUNT FROM T_MERCHANT";

	@PostConstruct
	void populateCache() {
		try {
			Connection conn = dataSource.getConnection();
			try {
				PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
				try {
					ResultSet rs = ps.executeQuery();
					try {
						while (rs.next()) {
							Merchant merchant = mapMerchant(rs);
							merchantCache.put(merchant.getNumber(), merchant);
						}
					} finally {
						rs.close();
					}
				} finally {
					ps.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@PreDestroy
	public void clearCache() {
		merchantCache.clear();
	}

	private Merchant mapMerchant(ResultSet rs) throws SQLException {
		Merchant merchant = new Merchant(
				rs.getString("NUMBER"), rs.getString("NAME"));
		merchant.setAmountPerPoint(rs.getBigDecimal("AMOUNT_PER_POINT"));
		merchant.setMinimumAmount(rs.getBigDecimal("MINIMUM_PURCHASE_AMOUNT"));
		// To support updates, unique ID must be restored
		/*
		Field idField = ReflectionUtils.findField(Merchant.class, "id");
		ReflectionUtils.makeAccessible(idField);
		ReflectionUtils.setField(
				idField, merchant, rs.getLong("ID"));
		*/
		return merchant;
	}

	long getCacheHits() {
		return merchantCacheHits;
	}

	boolean isMerchantCached(String merchantNumber) {
		return merchantCache.containsKey(merchantNumber);
	}

}
