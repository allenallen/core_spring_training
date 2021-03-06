package mapping.hibernate.usertype;

import java.time.MonthDay;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// TODO 04g: Study how the custom mapping is used in this entity

@Entity
public class Holiday {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String name;

	@org.hibernate.annotations.Columns(columns={
			@Column(name="month", nullable=false, length=2),
			@Column(name="day", nullable=false, length=2)
			})
	private MonthDay monthDay;
	
	public Holiday(String name, MonthDay monthDay) {
		this.name = name;
		this.monthDay = monthDay;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MonthDay getMonthDay() {
		return monthDay;
	}

	public void setMonthDay(MonthDay monthDay) {
		this.monthDay = monthDay;
	}

	public Long getId() {
		return id;
	}

	protected Holiday() { /* as needed by ORM/JPA */ }

}
