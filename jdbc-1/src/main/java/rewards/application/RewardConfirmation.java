package rewards.application;

import javax.money.MonetaryAmount;

public class RewardConfirmation {

	private final String confirmationNumber;
	private final String accountNumber;
	private final MonetaryAmount amount;
	private final int pointsEarned;
	private final int totalPoints;

	public RewardConfirmation(
			String confirmationNumber,
			String accountNumber,
			MonetaryAmount amount,
			int pointsEarned, int totalPoints) {
		super();
		this.confirmationNumber = confirmationNumber;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.pointsEarned = pointsEarned;
		this.totalPoints = totalPoints;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	
	public MonetaryAmount getAmount() {
		return amount;
	}

	public int getPointsEarned() {
		return pointsEarned;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

}
