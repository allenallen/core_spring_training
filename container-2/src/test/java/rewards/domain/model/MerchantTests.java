package rewards.domain.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rewards.application.Purchase;

public class MerchantTests {

	private Merchant merchant;

	@Before
	public void setUp() throws Exception {
		merchant = new Merchant(
				"1115558888", "Acme Supplies");
		merchant.setAmountPerPoint(new BigDecimal("50.00"));
		merchant.setMinimumAmount(new BigDecimal("500.00"));
	}

	@After
	public void tearDown() throws Exception {
	}

	private MonetaryAmount monetaryAmountOf(String amount) {
		return Monetary.getDefaultAmountFactory()
				.setNumber(new BigDecimal(amount))
				.setCurrency("USD")
				.create();
	}
	
	private Account account() {
		return null;
	}

	@Test
	public void rewardsPointsWithMinimumPurchaseAmount() {
		assertEquals(10,
				merchant.calculateRewardFor(account(),
						new Purchase(
								monetaryAmountOf("500.00"),
								merchant.getNumber(),
								"1234123412341234")));
	}

	@Test
	public void zeroPointsWhenBelowMinimumPurchaseAmount() {
		assertEquals(0,
				merchant.calculateRewardFor(account(),
						new Purchase(
								monetaryAmountOf("499.99"),
								merchant.getNumber(),
								"1234123412341234")));
	}

}
