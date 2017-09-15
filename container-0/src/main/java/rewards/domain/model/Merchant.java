package rewards.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import rewards.application.Purchase;

public class Merchant {

	private String number;
	private String name;

	private BigDecimal amountPerPoint; // amount that earns a point
	private BigDecimal minimumAmount; // amount before earning points

	/**
	 * Creates a merchant with the given number, and name.
	 *
	 * @param number Merchant number
	 * @param name Merchant name
	 */
	public Merchant(String number, String name) {
		this.number = number;
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}
	
	public BigDecimal getAmountPerPoint() {
		return amountPerPoint;
	}

	public void setAmountPerPoint(BigDecimal amountPerPoint) {
		this.amountPerPoint = amountPerPoint;
	}

	public BigDecimal getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(BigDecimal minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public int calculateRewardFor(Account account, Purchase purchase) {
		BigDecimal purchaseAmount = purchase.getAmount().getNumber().numberValue(BigDecimal.class);
		if (purchaseAmount.compareTo(minimumAmount) >= 0) {
			purchaseAmount = purchaseAmount.setScale(2);
			return purchaseAmount.divide(
					amountPerPoint.setScale(2), RoundingMode.DOWN).intValue();
		}
		return 0;
	}

}
