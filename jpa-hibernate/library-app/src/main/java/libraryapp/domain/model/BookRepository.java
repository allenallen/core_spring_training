package libraryapp.domain.model;

import java.util.Collection;

import org.springframework.data.repository.Repository;

public interface BookRepository extends Repository<Book, Long>{
	Book findBookByBarcode(String barcode);
	Collection<Book> findBooksByCategory(Category category);
	Collection<Book> findBooksByLoanStatus(boolean isBorrowed);
}
