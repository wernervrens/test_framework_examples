package mockito;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import domain.Contract;
import domain.ContractDao;


public class ContractMockitoTest extends TestCase {
    
    public void testQualifyForLoan() {
        //Given
        final Contract testContract = new Contract("John");
        final BigDecimal qualifyValue = new BigDecimal(5000);
        
        //And
        ContractDao daoMock = mockContractDao(testContract, qualifyValue);
        ReflectionTestUtils.setField(testContract, "dao", daoMock);
        
        //When
        final boolean qualifyForLoan = testContract.qualifyForLoan();
        
        //Then
        assertTrue(qualifyForLoan);
        Mockito.verify(daoMock).getPosition();
    }
    
    public void testNotQualifyForLoan() {
        //Given
        final Contract testContract = new Contract("John");
        final BigDecimal notQualifyValue = new BigDecimal(5);
        
        //And
        final ContractDao daoMock = mockContractDao(testContract, notQualifyValue);
        ReflectionTestUtils.setField(testContract, "dao", daoMock);
        
        //When
        final boolean qualifyForLoan = testContract.qualifyForLoan();
        
        //Then
        assertFalse(qualifyForLoan);
    }
    
    protected ContractDao mockContractDao(final Contract testContract, BigDecimal investorPosition) {
        final ContractDao daoMock = Mockito.mock(ContractDao.class);
        
        Mockito.when(daoMock.getPosition()).thenReturn(investorPosition);
        
        return daoMock;
    }
}
