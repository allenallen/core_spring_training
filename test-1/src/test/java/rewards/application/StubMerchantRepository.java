package rewards.application;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import rewards.domain.model.Merchant;
import rewards.domain.model.MerchantRepository;

@Repository
@Profile("stub")
public class StubMerchantRepository implements MerchantRepository {

	private Map<String, Merchant> merchants = new HashMap<>();

	public StubMerchantRepository() {
		this("1115558888");
	}

	/**
	 * Creates a repository with one merchant with the given merchant number.
	 * @param merchantNumber
	 */
	public StubMerchantRepository(String merchantNumber) {
		Merchant merchant = new Merchant(merchantNumber, "Acme Supplies");
		merchant.setAmountPerPoint(new BigDecimal("50.00"));
		merchant.setMinimumAmount(new BigDecimal("500.00"));
		merchants.put(merchant.getNumber(), merchant);
	}

	@Override
	public Merchant findByNumber(String merchantNumber) {
		Merchant merchant = merchants.get(merchantNumber);
		if (merchant == null) {
			throw new RuntimeException("Merchant not found");
		}
		return merchant;
	}

}
