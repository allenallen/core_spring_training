package rewards.application;

import rewards.domain.model.Account;
import rewards.domain.model.AccountRepository;
import rewards.domain.model.Merchant;
import rewards.domain.model.MerchantRepository;
import rewards.domain.model.RewardRepository;

public class RewardsApplicationServiceImpl implements RewardsApplicationService {

	private AccountRepository accountRepository;
	private MerchantRepository merchantRepository;
	private RewardRepository rewardRepository;

	public RewardsApplicationServiceImpl(
			AccountRepository accountRepository,
			MerchantRepository merchantRepository,
			RewardRepository rewardRepository) {
		super();
		this.accountRepository = accountRepository;
		this.merchantRepository = merchantRepository;
		this.rewardRepository = rewardRepository;
	}

	@Override
	public RewardConfirmation rewardAccountFor(Purchase purchase) {
		// TODO 01: Reward an account
		Account account;
		// TODO 01a: Retrieve the account by card number (use purchase.getCardNumber())
		// Hint: Use the accountRepository for this
		account = accountRepository.findByCardNumber(purchase.getCardNumber());
		
		// TODO 01b: Retrieve the merchant by number (use purchase.getMerchantNumber())
		// Hint: Use the merchantRepository for this
		Merchant merchant = merchantRepository.findByNumber(purchase.getMerchantNumber());
		
		// TODO 01c: Calculate reward for purchase (based on merchant's policies)
		int rewardPoints = merchant.calculateRewardFor(account, purchase);
		
		// TODO 01d: Credit the account
		account.credit(rewardPoints);
		
		// TODO 02: Return the corresponding reward confirmation
		RewardConfirmation rewardConfirmation = rewardRepository.confirmReward(purchase, account, rewardPoints);
		return rewardConfirmation;
	}

}
