package rewards.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// TODO 01: Mark this class as an entity and map it to the T_ACCOUNT table
public class Account {

	// TODO 02: Map this field as a primary key, let it be auto-generated, 
	//          (using @GeneratedValue) and map it to the column "id"
	private Long id;

	// TODO 03: Use annotations to map the remaining fields.  
	// Do all fields require annotations?
	private String number;
	private String name;
	private int totalPoints;
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

	// TODO 03a: Add a protected zero-arguments constructor,
	//           since this class does not have one.
	// It is a good practice to place a comment that
	// the zero-arguments constructor is required
	// by the persistence layer (JPA, in this case),
	// and is *not* added by design. The other
	// constructors with arguments *is* what is needed
	// in the domain module. Thus, keeping it protected
	// also helps prevent unwanted use.

}
