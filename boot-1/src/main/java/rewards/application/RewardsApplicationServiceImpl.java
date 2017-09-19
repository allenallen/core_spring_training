package rewards.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rewards.domain.model.Account;
import rewards.domain.model.AccountRepository;
import rewards.domain.model.Merchant;
import rewards.domain.model.MerchantRepository;
import rewards.domain.model.RewardRepository;

@Service("rewardsService")
public class RewardsApplicationServiceImpl implements RewardsApplicationService {

	private AccountRepository accountRepository;
	private MerchantRepository merchantRepository;
	private RewardRepository rewardRepository;

	@Autowired
	public RewardsApplicationServiceImpl(
			AccountRepository accountRepository,
			MerchantRepository merchantRepository,
			RewardRepository rewardRepository) {
		super();
		this.accountRepository = accountRepository;
		this.merchantRepository = merchantRepository;
		this.rewardRepository = rewardRepository;
	}

	@Transactional
	@Override
	public RewardConfirmation rewardAccountFor(Purchase purchase) {
		// Reward an account
		Account account = accountRepository.findByCardNumber(
				purchase.getCardNumber());
		Merchant merchant = merchantRepository.findByNumber(
				purchase.getMerchantNumber());
		int pointsEarned = merchant.calculateRewardFor(account, purchase);
		account.credit(pointsEarned);
		accountRepository.updateAccount(account);
		// Return the corresponding reward confirmation
		return rewardRepository.confirmReward(purchase, account, pointsEarned);
	}

}