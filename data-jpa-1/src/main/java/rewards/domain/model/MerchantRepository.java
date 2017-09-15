package rewards.domain.model;

// TODO 02b: Refactor this interface to take advantage of Spring Data JPA
public interface MerchantRepository {

	Merchant findByNumber(String merchantNumber);

}
