package mapping.basic;

import java.util.Date;

// TODO 01: Map basic data-members

// TODO 01a: Map the Person class as a persistent entity
public class Person {
	
	// TODO 01b1: Add an "id" field of type Long and map it as the primary key
	// TODO 01b3: Make the primary key generated (AUTO)

	private String firstName;
	private String lastName;
	// TODO 01c: Map the birth date as a DATE field (no hours-minutes-seconds)
	private Date birthDate;
	// TODO 01d: Map the gender (enum) as a STRING
	private Gender gender;
	
	// TODO 01f1: Add a constructor that accepts firstName and lastName arguments

	// TODO 01f3: Add a zero-args constructor to fix the error
	// Note: You can make the zero-args constructor public or protected.
	
	// TODO 01b2: Add a getter method for this "id" field

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
