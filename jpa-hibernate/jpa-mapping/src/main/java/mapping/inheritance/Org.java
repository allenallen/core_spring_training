package mapping.inheritance;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// TODO 03d: Map the Org class as a persistent entity

// TODO 03e3 (optional): Override the discriminator value (as "ORG")
@Entity
@DiscriminatorValue("ORG")
public class Org extends Party {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
