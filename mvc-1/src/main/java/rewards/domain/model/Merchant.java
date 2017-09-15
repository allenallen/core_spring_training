package rewards.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import rewards.application.Purchase;

@Entity
@Table(name="T_MERCHANT")
public class Merchant {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String number;
	private String name;

	@Column(name="amount_per_point")
	private BigDecimal amountPerPoint; // amount that earns a point
	@Column(name="minimum_purchase_amount")
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
	
	public Long getId() {
		return id;
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

	protected Merchant() {
		// required by persistence layer
	}

}
