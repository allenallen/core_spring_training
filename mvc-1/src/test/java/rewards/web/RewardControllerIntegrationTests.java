package rewards.web;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import rewards.application.RewardConfirmation;
import rewards.config.ApplicationConfig;
import rewards.config.InfrastructureConfig;
import rewards.config.WebMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={
		ApplicationConfig.class, InfrastructureConfig.class, WebMvcConfig.class })
public class RewardControllerIntegrationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testShowIndex() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("index"));
	}
	
	@Test
	public void testRewardProcess() throws Exception {
		
		ResultActions results = mockMvc.perform(MockMvcRequestBuilders.post("/reward")
				.param("purchaseAmount", "500.00")
				.param("merchantNumber", "1115558888")
				.param("cardNumber", "1234123412341234"));
		
		//Assert 200 OK, view name is reward, attribute rewardConfirmation exists 
		results.andExpect(MockMvcResultMatchers.status().isOk());
		results.andExpect(MockMvcResultMatchers.view().name("reward"));
		results.andExpect(MockMvcResultMatchers.model().attributeExists("rewardConfirmation"));

		//Assert response of controller with expected values
		RewardConfirmation actual = (RewardConfirmation) results.andReturn().getModelAndView().getModel().get("rewardConfirmation");
		assertEquals("1234567890", actual.getAccountNumber());
		assertEquals("1", actual.getConfirmationNumber());
		assertEquals(10, actual.getTotalPoints());
		assertEquals(10, actual.getPointsEarned());				

	}
	
	@Test 
	public void testNonExistentMerchantShowsErrorPage() throws Exception {
		ResultActions results = mockMvc.perform(MockMvcRequestBuilders.post("/reward")
				.param("purchaseAmount", "100.00")
				.param("merchantNumber", "doesNotExist")
				.param("cardNumber", "1234123412341234"));
		
		results.andExpect(MockMvcResultMatchers.status().isOk());
		results.andExpect(MockMvcResultMatchers.view().name("errors/notFound"));
	}

}
