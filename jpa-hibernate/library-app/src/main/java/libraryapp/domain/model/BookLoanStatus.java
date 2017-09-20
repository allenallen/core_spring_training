package libraryapp.domain.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BookLoanStatus {
	
	@ManyToOne
	private User user;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String barcode;
	private Date borrowDate;
	private Date dueDate;

	public BookLoanStatus(String title, String barcode, Date borrowDate, Date dueDate) {
		super();
		this.title = title;
		this.barcode = barcode;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
	}

	protected BookLoanStatus() {
	}

	public String getTitle() {
		return title;
	}

	public String getBarcode() {
		return barcode;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
