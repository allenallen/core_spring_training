package libraryapp.domain.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Embeddable;

@Embeddable
public class PublishDate {
	private final Map<Integer, String> months = new HashMap<>();

	protected PublishDate() {
	}

	public PublishDate(LocalDate date) {
		date.getMonthValue();
	}

	private String publishedMonth;
	private int publishedYear;

}
