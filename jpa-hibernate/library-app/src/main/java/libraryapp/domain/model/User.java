package libraryapp.domain.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	private String name;
	
	private String memberAccountId;

	@Enumerated(EnumType.STRING)
	private UserType type;

	public long getId() {
		return id;
	}

	public void setId(long memberAccountId) {
		this.id = memberAccountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public void setMemberAccountId(String memberAccountId) {
		this.memberAccountId = memberAccountId;
	}
	
	public String getMemberAccountId() {
		return this.memberAccountId;
	}

//	@OneToMany
//	private Collection<BookLoanStatus> booksBorrowed;
}
