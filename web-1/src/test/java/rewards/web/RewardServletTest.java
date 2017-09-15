package rewards.web;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import rewards.application.Purchase;
import rewards.application.RewardConfirmation;

public class RewardServletTest {

	private RewardServlet servlet;
	private StubRewardsApplicationService rewardsService;

	@Before
	public void setUp() throws Exception {
		servlet = new RewardServlet();
		rewardsService = new StubRewardsApplicationService();
		servlet.setRewardsApplicationService(rewardsService);
	}

	private MonetaryAmount monetaryAmountOf(String amount) {
		return Monetary.getDefaultAmountFactory()
				.setNumber(new BigDecimal(amount))
				.setCurrency("USD")
				.create();
	}

	@Test
	public void testDoPost() throws Exception {
		MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
		httpServletRequest.addParameter("purchaseAmount", "100.00");
		httpServletRequest.addParameter("merchantNumber", "1115558888");
		httpServletRequest.addParameter("cardNumber", "1234123412341234");

		MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
		
		servlet.doPost(httpServletRequest, httpServletResponse);
		
		Purchase actualPurchase = rewardsService.getPurchase();
		assertEquals(monetaryAmountOf("100.00"), actualPurchase.getAmount());
		assertEquals("1115558888", actualPurchase.getMerchantNumber());
		assertEquals("1234123412341234", actualPurchase.getCardNumber());

		RewardConfirmation results = (RewardConfirmation)
				httpServletRequest.getAttribute("rewardConfirmation");
		assertEquals("1234567890", results.getAccountNumber());
		assertEquals(2, results.getPointsEarned());
		assertEquals(2, results.getTotalPoints());
		assertEquals("1", results.getConfirmationNumber());
	}

}
