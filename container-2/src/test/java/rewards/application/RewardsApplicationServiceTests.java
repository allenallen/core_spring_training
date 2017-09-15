package rewards.application;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import rewards.config.ApplicationConfig;
import rewards.config.TestInfrastructureConfig;

public class RewardsApplicationServiceTests {

	private AbstractApplicationContext context;

	private RewardsApplicationService rewardsService;

	private String merchantNumber;
	private String cardNumber;
	private String accountNumber;

	@Before
	public void setUp() throws Exception {
		merchantNumber = "1115558888";
		cardNumber = "1234123412341234";
		accountNumber = "1234567890";

		context = new AnnotationConfigApplicationContext(
						ApplicationConfig.class, TestInfrastructureConfig.class);
		rewardsService = context.getBean(RewardsApplicationService.class);
		
		// : Uncomment the following to register a shutdown hook.
		/* This code causes the JVM to call the close logic on the application
		 * context whenever the JVM is closing.  
		 * Re-run the test in debug mode.  You should now reach
		 * the breakpoint inside the clearCache() method.
		 */
		 context.registerShutdownHook();
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

}
