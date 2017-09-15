package rewards.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="T_ACCOUNT")
public class Account {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String number;
	private String name;
	@Column(name = "total_points")
	private int totalPoints;

	@OneToMany
	@JoinColumn(name = "account_id")
	private Set<Card> cards = new HashSet<>();

	public Account(String number, String name) {
		this(number, name, 0);
	}

	public Account(String number, String name, int totalPoints) {
		super();
		this.number = number;
		this.name = name;
		this.totalPoints = totalPoints;
	}
	
	public Long getId() {
		return id;
	}

	public void credit(int points) {
		totalPoints += points;
	}

	public String getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public boolean addCard(String cardNumber) {
		return cards.add(new Card(cardNumber));
	}
	
	public Set<Card> getCards() {
		return Collections.unmodifiableSet(cards);
	}

	protected Account() {
		// required by persistence layer
	}

}
