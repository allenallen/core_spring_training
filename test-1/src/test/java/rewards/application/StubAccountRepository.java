package rewards.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import rewards.domain.model.Account;
import rewards.domain.model.AccountRepository;

@Repository
@Profile("stub")
public class StubAccountRepository implements AccountRepository {

	private Map<String, Account> accountsByCardNumber = new HashMap<>();

	public StubAccountRepository() {
		this("1234567890", "1234123412341234");
	}
	/**
	 * Creates a repository with one account using the given account number.
	 * The account is initialized with one card using the given card number.
	 * @param accountNumber
	 * @param cardNumber
	 */
	public StubAccountRepository(String accountNumber, String cardNumber) {
		Account account = new Account(accountNumber, "Juan Dela Cruz");
		account.addCard(cardNumber);
		accountsByCardNumber.put(cardNumber, account);
	}

	@Override
	public Account findByCardNumber(String cardNumber) {
		Account account = accountsByCardNumber.get(cardNumber);
		if (account == null) {
			throw new RuntimeException(
					"Account with card number [" + cardNumber + "] not found");
		}
		return account;
	}

	@Override
	public void updateAccount(Account account) {
		// nothing to do, everything is in memory
	}

}
