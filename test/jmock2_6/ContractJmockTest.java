package jmock2_6;

import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit3.MockObjectTestCase;
import org.springframework.test.util.ReflectionTestUtils;

import domain.Contract;
import domain.IContractDao;

public class ContractJmockTest extends MockObjectTestCase {

	public void testQualifyForLoan() {
		final Contract testContract = new Contract("John");
		final BigDecimal qualifyValue = new BigDecimal(5000);

		// Define expected behavior for ContractDao.
		// The mock needs to be an of an Interface
		// moving into method make assertions unclear - removed from flow
		final IContractDao daoMock = mockContractDao(qualifyValue);

		// insert into class under test
		ReflectionTestUtils.setField(testContract, "dao", daoMock);

		// execute code
		final boolean qualifyForLoan = testContract.qualifyForLoan();

		// run assertions
		assertTrue(qualifyForLoan);
	}

	public void testNotQualifyForLoan() {
		final Contract testContract = new Contract("John");
		final BigDecimal qualifyValue = new BigDecimal(5000);

		final Mockery context = new Mockery();
		final IContractDao daoMock = context.mock(IContractDao.class);

		context.checking(new Expectations() {

			{
				oneOf(daoMock).getPosition();
				will(returnValue(qualifyValue));
			}
		});

		ReflectionTestUtils.setField(testContract, "dao", daoMock);

		final boolean qualifyForLoan = testContract.qualifyForLoan();

		assertTrue(qualifyForLoan);
	}

	private IContractDao mockContractDao(final BigDecimal qualifyValue) {
		final Mockery context = new Mockery();
		final IContractDao daoMock = context.mock(IContractDao.class);

		context.checking(new Expectations() {

			{
				exactly(1).of(daoMock).getPosition();
				will(returnValue(qualifyValue));
			}
		});
		return daoMock;
	}
}