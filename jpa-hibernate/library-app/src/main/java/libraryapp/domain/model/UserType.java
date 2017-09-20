package libraryapp.domain.model;

public enum UserType {
	PROFESSOR(10), DEPARTMENT_HEAD(50), GRADUATE(2), UNDER_GRADUATE(10);

	private final int maxDaysAllowed;

	UserType(int maxDaysAllowed) {
		this.maxDaysAllowed = maxDaysAllowed;
	}

	int getValue() {
		return this.maxDaysAllowed;
	}

}
