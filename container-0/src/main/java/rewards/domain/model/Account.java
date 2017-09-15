package rewards.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Account {

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

}
