package jpa.plain;

import javax.persistence.Entity;
import javax.persistence.Id;

// TODO 01: Use JPA to define a persistent object/class
// TODO 01a: Map this class as a persistent class (annotate with Entity)
@Entity
public class Person {

	// TODO 01b: Map this field as the primary key (annotate with Id)
	@Id
	private Long id;
	// @javax.persistence.Basic
	private String name;

	public Person(long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected Person() { /* as needed by ORM/JPA */ }

}
