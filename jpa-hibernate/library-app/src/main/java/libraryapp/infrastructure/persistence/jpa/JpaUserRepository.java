package libraryapp.infrastructure.persistence.jpa;

import org.springframework.stereotype.Repository;

import libraryapp.domain.model.User;
import libraryapp.domain.model.UserRepository;

@Repository
public class JpaUserRepository implements UserRepository {

	@Override
	public User findUserById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
