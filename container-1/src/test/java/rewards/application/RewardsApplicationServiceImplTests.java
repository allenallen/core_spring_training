package rewards.application;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rewards.domain.model.AccountRepository;
import rewards.domain.model.MerchantRepository;
import rewards.domain.model.RewardRepository;

/**
 * Unit tests for the RewardsApplicationServiceImpl application logic.
 * Configures the implementation with stub repositories containing dummy data
 * for fast in-memory testing without the overhead of an external data source.
 * 
 * Besides helping catch bugs early, tests are a great way for a new developer
 * to learn an API as he or she can see the API in action. Tests also help
 * validate a design as they are a measure for how easy it is to use your code.
 */
public class RewardsApplicationServiceImplTests {

	private RewardsApplicationService rewardsService;

	private String merchantNumber;
	private String cardNumber;
	private String accountNumber;

	@Before
	public void setUp() throws Exception {
		merchantNumber = "1115558888";
		cardNumber = "1234123412341234";
		accountNumber = "1234567890";

		AccountRepository accountRepository = new StubAccountRepository(accountNumber, cardNumber);
		MerchantRepository merchantRepository = new StubMerchantRepository(merchantNumber);
		RewardRepository rewardRepository = new StubRewardRepository();

		rewardsService = new RewardsApplicationServiceImpl(
				accountRepository, merchantRepository, rewardRepository);
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

	@Test
	public void testZeroRewardForPurchaseBelowMinimumAmount() {
		RewardConfirmation rewardConfirmation =
				rewardsService.rewardAccountFor(purchaseOf("499.00"));

		// assert the expected reward confirmation results
		assertNotNull(rewardConfirmation);
		assertNotNull(rewardConfirmation.getConfirmationNumber());

		// assert that reward is given to "1234567890"
		assertEquals(accountNumber, rewardConfirmation.getAccountNumber());

		// assert no reward points (below minimum purchase amount)
		assertEquals(0, rewardConfirmation.getPointsEarned());
	}

	@Test(expected=Exception.class)
	public void testAccountNotFound() {
		Purchase purchase = new Purchase(
				monetaryAmountOf("100.00"),
				merchantNumber, "NON-EXISTENT CARD");
		rewardsService.rewardAccountFor(purchase);
	}

	@Test(expected=Exception.class)
	public void testMerchantNotFound() {
		Purchase purchase = new Purchase(
				monetaryAmountOf("100.00"),
				"NON-EXISTENT MERCHANT", cardNumber);
		rewardsService.rewardAccountFor(purchase);
	}

}
