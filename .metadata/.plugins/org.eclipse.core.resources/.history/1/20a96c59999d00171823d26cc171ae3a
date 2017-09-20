package libraryapp.domain.service;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryapp.domain.model.BookLoanStatus;
import libraryapp.domain.model.BookRepository;
import libraryapp.domain.model.UserRepository;
import libraryapp.interfaces.BorrowReceipt;
import libraryapp.interfaces.BorrowingServiceFacade;
import libraryapp.interfaces.ReservationReceipt;

@Service
public class BorrowService implements BorrowingServiceFacade {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public BorrowReceipt borrowBook(String barcode, String memberAccountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void returnBook(String barcode, String memberAccountId) {
		// TODO Auto-generated method stub

	}

	@Override
	public ReservationReceipt reserveBook(String isbn, String memberAccountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<BookLoanStatus> getBookLoanStatus(String memberAccountId) {
		// TODO Auto-generated method stub
		return null;
	}

}
