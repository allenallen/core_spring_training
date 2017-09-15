package rewards.application;

import java.util.Random;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import rewards.domain.model.Account;
import rewards.domain.model.RewardRepository;

@Repository
@Profile("stub")
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
