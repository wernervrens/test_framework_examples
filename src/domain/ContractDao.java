package domain;

import java.math.BigDecimal;


public class ContractDao implements IContractDao {
    
    @Override
    public BigDecimal getPosition() {
        return new BigDecimal(2000);
    }
    
}
