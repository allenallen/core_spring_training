package rewards.domain.model;

public interface AccountRepository {

	Account findByCardNumber(String cardNumber);

	void updateAccount(Account account);

}
