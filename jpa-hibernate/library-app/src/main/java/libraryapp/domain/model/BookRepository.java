package libraryapp.domain.model;

import java.util.Collection;

public interface BookRepository {
	Book findBookByBarcode(String barcode);
	Collection<Book> findBooksByCategory(Category category);
	Collection<Book> findBooksByLoanStatus(boolean isBorrowed);
}
