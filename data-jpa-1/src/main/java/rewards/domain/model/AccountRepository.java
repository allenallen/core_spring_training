package rewards.domain.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

// TODO 02a: Refactor this interface to take advantage of Spring Data JPA
public interface AccountRepository extends Repository<Account, Long> {

	@Query("SELECT a FROM Account a WHERE a.cards.number = ?1")
	Account findByCardNumber(String cardNumber);

	Account save(Account account);

}
