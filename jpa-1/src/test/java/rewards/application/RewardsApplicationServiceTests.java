package rewards.application;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rewards.config.SystemTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SystemTestConfig.class)
public class RewardsApplicationServiceTests {

	@Autowired
	private RewardsApplicationService rewardsService;

	private String merchantNumber = "1115558888";
	private String cardNumber = "1234123412341234";
	private String accountNumber = "1234567890";

	private MonetaryAmount monetaryAmountOf(String amount) {
		return Monetary.getDefaultAmountFactory()
				.setNumber(new BigDecimal(amount))
				.setCurrency("USD")
				.create();
	}

	private Purchase purchaseOf(String amount) {
		return new Purchase(
				monetaryAmountOf(amount),
				merchantNumber, cardNumber);
	}

	@Test
	public void testRewardForPurchase() {
		RewardConfirmation rewardConfirmation =
				rewardsService.rewardAccountFor(purchaseOf("500.00"));

		// assert the expected reward confirmation results
		assertNotNull(rewardConfirmation);
		assertNotNull(rewardConfirmation.getConfirmationNumber());

		// assert that reward is given to "1234567890"
		assertEquals(accountNumber, rewardConfirmation.getAccountNumber());

		// assert reward of 10 points (50 bucks per point)
		assertEquals(10, rewardConfirmation.getPointsEarned());
	}

	// TODO 14a: Remove the @Ignore annotation below. Run this JUnit test.
	// What exception was thrown?
	// Is it different from the expected exception in JpaAccountRepositoryTests?
	// If so, why?
	@Ignore
	@Test(expected=EmptyResultDataAccessException.class)
	public void testAccountNotFound() {
		Purchase purchase = new Purchase(
				monetaryAmountOf("100.00"),
				merchantNumber, "NON-EXISTENT CARD");
		rewardsService.rewardAccountFor(purchase);
	}

	// TODO 14b: Remove the @Ignore annotation below. Run this JUnit test.
	// What exception was thrown?
	// Is it different from the expected exception in JpaMerchantRepositoryTests?
	// If so, why?
	@Ignore
	@Test(expected=EmptyResultDataAccessException.class)
	public void testMerchantNotFound() {
		Purchase purchase = new Purchase(
				monetaryAmountOf("100.00"),
				"NON-EXISTENT MERCHANT", cardNumber);
		rewardsService.rewardAccountFor(purchase);
	}

}
