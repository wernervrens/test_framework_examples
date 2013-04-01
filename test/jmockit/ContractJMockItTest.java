package jmockit;

import java.math.BigDecimal;

import junit.framework.TestCase;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import domain.Contract;
import domain.ContractDao;

public class ContractJMockItTest extends TestCase {
	@Mocked
	ContractDao daoMock;

	final BigDecimal qualifyValue = new BigDecimal(5000);
	final BigDecimal nonQualifyValue = new BigDecimal(5000);

	public void testQualifyForLoan() {
		// Given
		final Contract testContract = new Contract("John");

		// And
		new NonStrictExpectations() {
			{
				daoMock.getPosition();
				result = qualifyValue;
			}
		};

		// When
		final boolean qualifyForLoan = testContract.qualifyForLoan();

		// Then
		assertTrue(qualifyForLoan);

		new Verifications() {
			{
				daoMock.getPosition();
				times = 1;
			}
		};
	}

	public void testNonQualifyForLoan() {
		// Given
		final Contract testContract = new Contract("John");

		// And
		new NonStrictExpectations() {

			{
				daoMock.getPosition();
				result = qualifyValue;
				times = 1;
			}
		};

		// When
		final boolean qualifyForLoan = testContract.qualifyForLoan();

		// Then
		assertTrue(qualifyForLoan);
	}
}
