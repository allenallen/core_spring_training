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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookLoanStatus other = (BookLoanStatus) obj;
		if (barcode == null) {
			if (other.barcode != null)
				return false;
		} else if (!barcode.equals(other.barcode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
