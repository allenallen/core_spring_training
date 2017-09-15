package rewards.web;

import java.math.BigDecimal;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import rewards.application.Purchase;
import rewards.application.RewardsApplicationService;

// TODO 06: Annotate the class as a controller
public class RewardController {
	
	private RewardsApplicationService rewardsService;

	@Autowired
	public RewardController(RewardsApplicationService rewardsService) {
		this.rewardsService = rewardsService;
	}

	// TODO 07a: Map this to the root url '/'
	// TODO 07b: Make this method return index.jsp as the view
	public String showIndex() {
		return "index";
	}
	
	private MonetaryAmount monetaryAmountOf(String amount) {
		return Monetary.getDefaultAmountFactory()
				.setNumber(new BigDecimal(amount))
				.setCurrency("USD")
				.create();
	}

	// TODO 08a: Map this to '/reward'
	// TODO 08b: Make this method only accept POST requests (use the "method" attribute in the annotation and set it to RequestMethod.POST)
	// TODO 08c: Make the return value of rewardsService#rewardAccountFor() accessible by the view
	// TODO 08d: Make this method return reward.jsp as the view
	public String processReward(String amount, 
			String merchantNumber, 
			String cardNumber, Model model) {
		
		Purchase purchase = new Purchase(
				monetaryAmountOf(amount), merchantNumber, cardNumber);
		rewardsService.rewardAccountFor(purchase);
		return null;
	}
	
	// TODO 09a: Add an ExceptionHandler that will catch EmptyResultDataAccessException
	// TODO 09b: Make it return the view notFound.jsp
	public String entityNotFound() {
		return "errors/notFound";
	}

	// After completing the steps, save all work and run this web app.
	// Refer to rewards/test-data.sql to determine what
	// card number and merchant number will work
	// when form is submitted.
}
