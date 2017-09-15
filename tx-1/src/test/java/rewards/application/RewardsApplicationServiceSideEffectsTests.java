package rewards.application;

import java.math.BigDecimal;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rewards.config.SystemTestConfig;

// TODO 05: Make this side-effect free by adding @Transactional
// Otherwise, when this is run along with other tests, the subsequent tests will fail
// (since other data is inserted into the database)
@ContextConfiguration(classes=SystemTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RewardsApplicationServiceSideEffectsTests {

	@Autowired
	private RewardsApplicationService rewardsService;

	private String merchantNumber;
	private String cardNumber;

	@Before
	public void setUp() throws Exception {
		merchantNumber = "1115558888";
		cardNumber = "1234123412341234";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1stTime() throws Exception {
		doRewardForPurchase();
	}

	@Test
	public void test2ndTime() throws Exception {
		doRewardForPurchase();
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

	private void doRewardForPurchase() {
		rewardsService.rewardAccountFor(
				purchaseOf("500.00"));
	}

}
