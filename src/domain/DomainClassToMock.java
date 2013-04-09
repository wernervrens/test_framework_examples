package domain;

import java.math.BigDecimal;

public class DomainClassToMock {

	private static String staticField = "the original static value";
	private final String finalField = "the final value";

	public static enum SignEnum {
		POSITIVE, NEGATIVE, NEUTRAL;
	}

	public BigDecimal getSomeReturnValue() {
		return new BigDecimal(1);
	}
	
	public SignEnum getSomeReturnValueBasedOnParm(int input) {
		if (input > 0) {
			return SignEnum.NEGATIVE;
		} else if (input == 0) {
			return SignEnum.NEUTRAL;
		} else
			return SignEnum.POSITIVE;
	}
	
	public boolean methodWithClassParam(Class aClass) {
		return false;
	}

	public static String getStaticFieldValue() {
		return staticField;
	}

	public final String getFinalFieldValue() {
		return finalField;
	}

}
