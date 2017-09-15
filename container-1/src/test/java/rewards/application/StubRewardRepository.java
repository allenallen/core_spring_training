package rewards.application;

import java.util.Random;

import rewards.domain.model.Account;
import rewards.domain.model.RewardRepository;

public class StubRewardRepository implements RewardRepository {

	@Override
	public RewardConfirmation confirmReward(Purchase purchase, Account account, int pointsEarned) {
		return new RewardConfirmation(
				new Random().toString(),
				account.getNumber(),
				purchase.getAmount(),
				pointsEarned,
				account.getTotalPoints());
	}

}
