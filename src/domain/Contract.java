package domain;

import java.math.BigDecimal;


public class Contract {
    
    private final BigDecimal loanThreshold;
    
    private IContractDao dao;
    
    public Contract(String investor) {
    	initBeans();
        
        loanThreshold = new BigDecimal(1000);
    }
    
    
    public boolean qualifyForLoan() {
        if (getPosition().compareTo(loanThreshold) > 0) {
            return true;
        }
        
        return false;
    }
    
    
    public BigDecimal getPosition() {
        return dao.getPosition();
    }
    
    private void initBeans() {
        dao = new ContractDao();
        
    }
}
