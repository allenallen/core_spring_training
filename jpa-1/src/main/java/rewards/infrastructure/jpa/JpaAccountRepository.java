package rewards.infrastructure.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import rewards.domain.model.Account;
import rewards.domain.model.AccountRepository;

@Repository
public class JpaAccountRepository implements AccountRepository {

	/*
	private static final String SQL_FINDBY_CARDNUMBER =
			"SELECT a.ID AS ID, a.NUMBER AS NUMBER, a.NAME AS NAME, a.TOTAL_POINTS AS TOTAL_POINTS,"
					+ " b.NUMBER AS CARD_NUMBER, b.ID AS CARD_ID"
					+ " FROM T_ACCOUNT a, T_ACCOUNT_CARD c"
					+ " LEFT OUTER JOIN T_ACCOUNT_CARD b ON a.ID = b.ACCOUNT_ID"
					+ " WHERE c.NUMBER = :cardNumber AND c.ACCOUNT_ID = a.ID";
	*/

    private EntityManager entityManager;

	/**
	 * Sets the entity manager. Assumes automatic dependency injection via the
	 * JPA @PersistenceContext annotation. However this method may still be
	 * called manually in a unit-test. Thus, the default (package private)
	 * method visibility.
	 * 
	 * @param entityManager
	 */
    @PersistenceContext
    void setEntityManager(EntityManager entityManager) {
    	this.entityManager = entityManager;
    }

	@Override
	public Account findByCardNumber(String cardNumber) {
		// TODO 07: Use the entityManager to search for an Account with the given credit card number.
	    // Hint: Refer to the native SQL defined above.
		return null;

		// Run the JpaAccountRepositoryTests when done.
		// It should pass.
	}
	
	// TODO 15: (BONUS) Redo the query (in #07) to use the Criteria Query API instead
	// This makes your queries more type-safe and less prone to
	// errors caused by column renaming.
	// Run the JpaAccountRepositoryTests when done.
	// It should pass.
	
	@Override
	public Account updateAccount(Account account) {
		return entityManager.merge(account);
	}

}
