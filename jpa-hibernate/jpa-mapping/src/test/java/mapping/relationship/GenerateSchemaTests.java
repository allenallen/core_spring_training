package mapping.relationship;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class GenerateSchemaTests {

	@PersistenceContext
	private EntityManager entityManager;

	@Before
	public void setUp() throws Exception {
		assertNotNull(entityManager);
	}

	@After
	public void tearDown() throws Exception {
	}

	// TODO 02: Map bi-directional multi-valued association between Department and Employee

	@Test
	public void testGenerateSchema() throws Exception {
		entityManager.getMetamodel().entity(Employee.class);
		entityManager.getMetamodel().entity(Department.class);
	}

	// TODO 02c: Run these tests and see the output
	// Which table had a foreign key?

	@Test
	@Sql(statements={
		"INSERT INTO Department (id, name)"
				+ " VALUES (1, 'Human Resources')",
		"INSERT INTO Department (id, name)"
				+ " VALUES (2, 'Executive Committee')",
		"INSERT INTO Employee (id, firstName, lastName, department_id)"
				+ " VALUES (18, 'Bob', 'Jones', 2)",
		"INSERT INTO Employee (id, firstName, lastName, department_id)"
				+ " VALUES (42, 'John', 'Smith', 1)"
	})
	public void addEmployee() throws Exception {
		Employee employee = new Employee(51);
		employee.setFirstName("Cindy");
		employee.setLastName("Clarkson");
		Department hr = entityManager.find(Department.class, 1L);
		hr.getEmployees().add(employee);
		employee.setDepartment(hr);
		entityManager.persist(employee);
		entityManager.flush(); // just so SQL commands can be seen
	}

}
