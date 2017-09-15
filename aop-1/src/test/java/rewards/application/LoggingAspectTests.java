package rewards.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rewards.config.SystemTestConfig;
import rewards.domain.model.AccountRepository;

@ContextConfiguration(classes={ SystemTestConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class LoggingAspectTests {

	@Autowired
	private AccountRepository repository;
	
	@Test
	public void testLogger() {
		repository.findByCardNumber("1234123412341234");
	}

}
