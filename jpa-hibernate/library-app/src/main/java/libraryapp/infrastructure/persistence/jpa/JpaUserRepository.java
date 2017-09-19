package libraryapp.infrastructure.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

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
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(User arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends User> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exists(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<User> findAll(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findOne(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
