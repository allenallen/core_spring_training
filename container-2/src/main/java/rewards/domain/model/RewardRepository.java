package rewards.domain.model;

import rewards.application.Purchase;
import rewards.application.RewardConfirmation;

public interface RewardRepository {

	/**
	 * Returns reward confirmation.
	 *
	 * @param purchase
	 * @param account
	 * @param pointsEarned
	 * @return reward confirmation
	 */
	RewardConfirmation confirmReward(
			Purchase purchase, Account account, int pointsEarned);

}
