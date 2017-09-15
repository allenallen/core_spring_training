package rewards.infrastructure.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import rewards.domain.model.Merchant;
import rewards.domain.model.MerchantRepository;

@Repository
public class JpaMerchantRepository implements MerchantRepository {

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

	/*
	private static final String SQL_FINDBY_NUMBER =
			"SELECT m.ID AS ID, m.NUMBER AS NUMBER, m.NAME AS NAME, m.AMOUNT_PER_POINT AS AMOUNT_PER_POINT, m.MINIMUM_PURCHASE_AMOUNT AS MINIMUM_PURCHASE_AMOUNT"
					+ " FROM T_MERCHANT m"
					+ " WHERE m.NUMBER = :merchantNumber";
	*/
	
	@Override
	public Merchant findByNumber(String merchantNumber) {
		// TODO 09: Use the entityManager to search for a Merchant with the given number.
	    // Hint: Refer to the native SQL defined above.
		TypedQuery<Merchant> query = entityManager.createQuery(
				"SELECT m FROM Merchant m WHERE m.number = :merchantNumber",
				Merchant.class);
		query.setParameter("merchantNumber", merchantNumber);
		return query.getSingleResult();

		// Run the JpaMerchantRepositoryTests when done.
		// It should pass.
	}

}
