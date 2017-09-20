package libraryapp.infrastructure.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import libraryapp.domain.model.BookLoanStatus;
import libraryapp.domain.model.User;
import libraryapp.domain.model.UserRepository;

@Repository
public class JpaUserRepository implements UserRepository {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public User findUserByMemberId(String id) {
		TypedQuery<User> query = em.createQuery("SELECT user FROM User user WHERE " + "user.memberAccountId = :id",
				User.class);
		query.setParameter("id", id);

		return query.getSingleResult();

	}

	@Override
	@Transactional
	public void save(User user) {
		em.merge(user);
	}

	@Transactional
	@Override
	public void returnBook(String barcode, User user) {
		// TypedQuery<BookLoanStatus> query = em
		// .createQuery("SELECT a FROM User u JOIN BookLoanStatus b ON id WHERE u.id = "
		// + user.getId()
		// + " AND u.bookLoanStatus.barcode = :barcode", BookLoanStatus.class);
		// query.setParameter("barcode", barcode);
		// BookLoanStatus bls = query.getSingleResult();
		// user.returnBook(bls);
		BookLoanStatus bls = user.getBookLoanStatus().stream()
				.filter((bookLoan) -> bookLoan.getBarcode().equals(barcode)).findFirst().get();
		System.out.println(user.getBookLoanStatus().size());
		user.returnBook(bls);
		System.out.println(user.getBookLoanStatus().size());
		em.merge(user);
	}

}
