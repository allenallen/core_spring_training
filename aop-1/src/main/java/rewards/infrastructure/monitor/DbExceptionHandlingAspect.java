package rewards.infrastructure.monitor;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import rewards.domain.model.RewardDataAccessException;

@Aspect
@Component
public class DbExceptionHandlingAspect {

	private Logger logger = LoggerFactory.getLogger(getClass());

	//  (Optional): Configure this advice method to handle 
	// all exceptions thrown by Repository class methods.
	// Select the advice type that seems most appropriate.
	@AfterThrowing(pointcut="execution(* rewards..*Repository+.*(..))", throwing="e")
	public void implExceptionHandling(RewardDataAccessException e) { 
		logger.info("Sending an email to Mister Smith : " + e + "\n");
	}

	//  (Optional): Annotate this class as a Spring-managed bean.
	// Note that we enabled component scanning in an earlier step.
	// Save all work, run DbExceptionHandlingAspectTests. It should pass, and 
	// expected logging output should appear in the console.

}
