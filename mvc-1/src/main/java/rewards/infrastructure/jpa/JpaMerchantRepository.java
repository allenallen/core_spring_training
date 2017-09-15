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

	@Override
	public Merchant findByNumber(String merchantNumber) {
		TypedQuery<Merchant> query = entityManager.createQuery(
				"SELECT m FROM Merchant m WHERE m.number = :merchantNumber",
				Merchant.class);
		query.setParameter("merchantNumber", merchantNumber);
		return query.getSingleResult();
	}

}
