package libraryapp.domain.model;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
	User findUserByMemberId(String id);

	void returnBook(String barcode, User user);
	
	void save(User user);
}
