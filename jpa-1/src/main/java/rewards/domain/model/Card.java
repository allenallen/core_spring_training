package rewards.domain.model;

// TODO 04: Mark this class as an entity and map it to the T_ACCOUNT_CARD table
public class Card {

	// TODO 05: Map this field as a primary key, let it be auto-generated, 
	//          (using @GeneratedValue) and map it to the column "id"
	private Long id;

	private String number;

	public Card(String number) {
		if (number == null || number.isEmpty()) {
			throw new IllegalArgumentException("Card number cannot be null");
		}
		// To keep things simple, no MOD-10 check is done.
		this.number = number;
	}
	
	public Long getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		Card other = (Card) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	// TODO 06a: Add a protected zero-arguments constructor
	// Why? Is this needed?

}
