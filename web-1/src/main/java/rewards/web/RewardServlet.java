package rewards.web;

import java.io.IOException;
import java.math.BigDecimal;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import rewards.application.Purchase;
import rewards.application.RewardConfirmation;
import rewards.application.RewardsApplicationService;

@SuppressWarnings("serial")
public class RewardServlet extends HttpServlet {

	private RewardsApplicationService rewardsService;

	@Override
	public void init() throws ServletException {
		// TODO 3a Use WebApplicationContextUtils to retrieve the ApplicationContext
		// TODO 3b Use the ApplicationContext to retrieve the RewardsApplicationService
		ApplicationContext context =
				WebApplicationContextUtils.getRequiredWebApplicationContext(
						getServletContext());
		setRewardsApplicationService(
				context.getBean(RewardsApplicationService.class));
		
		// After completing the steps, save all work and run this web app.
		// Refer to rewards/test-data.sql to determine what
		// card number and merchant number will work
		// when form is submitted.
	}

	public void setRewardsApplicationService(RewardsApplicationService rewardsService) {
		this.rewardsService = rewardsService;
	}

	public RewardsApplicationService getRewardsApplicationService() {
		return rewardsService;
	}

	private MonetaryAmount monetaryAmountOf(String amount) {
		return Monetary.getDefaultAmountFactory()
				.setNumber(new BigDecimal(amount))
				.setCurrency("USD")
				.create();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String purchaseAmount = req.getParameter("purchaseAmount");
		String merchantNumber = req.getParameter("merchantNumber");
		String cardNumber = req.getParameter("cardNumber");
		
		Purchase purchase = new Purchase(
				monetaryAmountOf(purchaseAmount), merchantNumber, cardNumber);

		RewardConfirmation confirmation =
				getRewardsApplicationService().rewardAccountFor(purchase);
		req.setAttribute("rewardConfirmation", confirmation);

		RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/views/reward.jsp");		
		view.forward(req, resp);	
	}
	
}
