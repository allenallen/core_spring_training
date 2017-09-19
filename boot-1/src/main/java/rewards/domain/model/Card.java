package rewards.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="T_ACCOUNT_CARD",
		uniqueConstraints={
				@UniqueConstraint(columnNames={ "account_id", "number" }) })
public class Card {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String number;

	public Card(String number) {
		if (number == null || number.isEmpty()) {
			throw new IllegalArgumentException("Card number cannot be null");
		}
		// To keep things simple, no MOD-10 check is done.
		this.number = number;
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

	protected Card() {
		// required by persistence layer
	}

}
