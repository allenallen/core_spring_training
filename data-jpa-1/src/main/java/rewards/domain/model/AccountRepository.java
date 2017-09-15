package rewards.domain.model;

// TODO 02a: Refactor this interface to take advantage of Spring Data JPA
public interface AccountRepository {

	Account findByCardNumber(String cardNumber);

	Account updateAccount(Account account);

}
