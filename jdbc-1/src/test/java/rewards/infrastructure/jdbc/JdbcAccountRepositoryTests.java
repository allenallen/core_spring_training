package rewards.infrastructure.jdbc;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import rewards.domain.model.Account;

public class JdbcAccountRepositoryTests {

	private JdbcAccountRepository accountRepository;

	@Before
	public void setUp() throws Exception {
		accountRepository = new JdbcAccountRepository(
				createTestDataSource());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindAccountByCard() {
		Account account = accountRepository.findByCardNumber(
				"1234123412341234");
		assertNotNull(account);
		assertEquals("1234567890", account.getNumber());
		assertEquals("Juan Dela Cruz", account.getName());
		assertEquals(1, account.getCards().size());
		assertEquals(0, account.getTotalPoints());
	}

	@Test(expected=EmptyResultDataAccessException.class)
	public void throwsExceptionWhenCardNumberNotFound() throws Exception {
		accountRepository.findByCardNumber("NON-EXISTENT CARD");
	}
	
	@Test
	public void updateAccount() throws Exception {
		Account account = new Account("1234567890", "Juan Dela Cruz", 0);
		accountRepository.updateAccount(account);
	}

	@Test(expected=IncorrectResultSizeDataAccessException.class)
	public void throwsExceptionWhenUpdatingNonExistingAccount() throws Exception {
		Account account = new Account("bogus", "Juan Dela Cruz", 0);
		accountRepository.updateAccount(account);
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder()
			.setName("rewards")
			.addScript("/rewards/testdb/schema.sql")
			.addScript("/rewards/testdb/test-data.sql")
			.build();
	}

}
