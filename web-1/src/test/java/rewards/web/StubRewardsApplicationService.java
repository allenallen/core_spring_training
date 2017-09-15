package rewards.web;

import rewards.application.Purchase;
import rewards.application.RewardConfirmation;
import rewards.application.RewardsApplicationService;

public class StubRewardsApplicationService implements RewardsApplicationService {

	private Purchase purchase;

	public Purchase getPurchase() {
		return purchase;
	}

	@Override
	public RewardConfirmation rewardAccountFor(Purchase purchase) {
		this.purchase = purchase;
		return new RewardConfirmation("1", "1234567890", purchase.getAmount(), 2, 2);
	}

}
