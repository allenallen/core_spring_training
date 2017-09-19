package libraryapp.infrastructure.persistence.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import libraryapp.domain.model.Book;
import libraryapp.domain.model.BookRepository;
import libraryapp.domain.model.Category;

@Repository
public class JpaBookRepository implements BookRepository{

	@PersistenceContext
	protected EntityManager em;
	
	@Override
	public Book findBookByBarcode(String barcode) {
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.barcode = :barcode",Book.class);
		query.setParameter("barcode", barcode);
		return query.getSingleResult();
	}

	@Override
	public Collection<Book> findBooksByCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Book> findBooksByLoanStatus(boolean isBorrowed) {
		// TODO Auto-generated method stub
		return null;
	}

}
