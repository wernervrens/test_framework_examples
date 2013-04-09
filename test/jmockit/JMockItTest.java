package jmockit;

import java.math.BigDecimal;

import junit.framework.TestCase;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.springframework.test.util.ReflectionTestUtils;

import domain.DomainClassToInstantiate;
import domain.DomainClassToMock;
import domain.DomainClassToMock.SignEnum;

/**
 * 
 * This class serves to provide example code of typical JMockit usages.
 * More documentation can be found at: 
 *    http://jmockit.googlecode.com/svn/trunk/www/tutorial/BehaviorBasedTesting.html
 *    http://lmgtfy.com/?q=jmockit+examples
 * 
 * Feel free to add more examples.
 *
 * @author vern
 */
public class JMockItTest extends TestCase {
	/**
	 * When an object is annotated like this, all invocations/usages in this
	 * test will be against this mocked instance. This holds true even in cases
	 * where the method called is not explicitly covered by an Expectation. In
	 * this case some default value will be returned, (null included).
	 */
	@Mocked
	DomainClassToMock mockedObject;

	/**
	 * The most used case is to return some test value when a method on amocked
	 * object is called.
	 * 
	 * This will probably cover 90% of mock usage.
	 */
	public void testMockMethodRetunValue() {
		// Given
		final BigDecimal expectedPosition = new BigDecimal(999);

		new NonStrictExpectations() {
			{
				mockedObject.getSomeReturnValue();
				result = expectedPosition;
			}
		};

		// When
		BigDecimal actualPosition = mockedObject.getSomeReturnValue();

		// Then
		assertEquals(expectedPosition, actualPosition);
	}

	/**
	 * In some cases one needs to verify that a call, or specific number of
	 * calls to some mocked object was made. In that case one uses a
	 * Verifications clauses at the end of the test along with the assertions.
	 * This can also be included in the Expectations, but makes for difficult
	 * reading as it breaks the "//Given //When //Then" flow
	 */
	public void testCallWasMadeToMethod() {
		// Given
		final BigDecimal expectedPosition = new BigDecimal(999);

		new NonStrictExpectations() {
			{
				mockedObject.getSomeReturnValue();
				result = expectedPosition;
			}
		};

		// When
		BigDecimal actualPosition = mockedObject.getSomeReturnValue();

		// Then
		assertEquals(expectedPosition, actualPosition);

		// And
		new Verifications() {
			{
				mockedObject.getSomeReturnValue();
				times = 1;
			}
		};
	}

	/**
	 * A ground-breaking game-changer feature for mocking!!! I could not think
	 * though of a proper example and the application in this test is totally
	 * just to show how its done although its a totally non-sense application
	 */
	public void testMockStaticField() {
		// Given
		final String expectedStaticValue = "mocked static value";

		// And
		new NonStrictExpectations() {
			{
				DomainClassToMock.getStaticFieldValue();
				result = expectedStaticValue;
			}
		};

		// When
		String injectedStaticValue = DomainClassToMock.getStaticFieldValue();

		// Then
		assertEquals(injectedStaticValue,
				DomainClassToMock.getStaticFieldValue());
	}

	/**
	 * pretty cool to change final values for a test! :)
	 */
	public void testMockFinalField() {
		// Given
		final String expectedFinalValue = "mocked final value";

		// And
		new NonStrictExpectations() {
			{
				mockedObject.getFinalFieldValue();
				result = expectedFinalValue;
			}
		};

		// When
		String injectedFinalValue = mockedObject.getFinalFieldValue();

		// Then
		assertEquals(injectedFinalValue, mockedObject.getFinalFieldValue());
	}

	/**
	 * Sometimes one needs a method to return a specific value based on the
	 * input params.
	 */
	public void testParameterValue() {

		// Given
		new NonStrictExpectations() {

			{
				mockedObject.getSomeReturnValueBasedOnParm(1);
				returns(DomainClassToMock.SignEnum.NEGATIVE);

				mockedObject.getSomeReturnValueBasedOnParm(2);
				returns(DomainClassToMock.SignEnum.NEUTRAL);

				mockedObject.getSomeReturnValueBasedOnParm(-22);
				returns(DomainClassToMock.SignEnum.POSITIVE);
			}
		};

		// When
		SignEnum firstResult = mockedObject.getSomeReturnValueBasedOnParm(2);
		SignEnum secondResult = mockedObject.getSomeReturnValueBasedOnParm(-22);
		SignEnum thirdResult = mockedObject.getSomeReturnValueBasedOnParm(1);

		// Then
		assertEquals(DomainClassToMock.SignEnum.NEUTRAL, firstResult);
		assertEquals(DomainClassToMock.SignEnum.POSITIVE, secondResult);
		assertEquals(DomainClassToMock.SignEnum.NEGATIVE, thirdResult);
	}

	/**
	 * Sometimes one requires to do more complex analysis based on the input
	 * parameters. For those its possible to write your own Delegate.
	 */
	public void testClassArguments() {
		// Given
		new NonStrictExpectations() {
			{
				// define an expectation of which method is going to be called
				mockedObject.methodWithClassParam((Class) any);

				// implement a Delegate where one can capture and evaluate the
				// input param
				result = new mockit.Delegate<Class<?>>() {
					boolean methodWithClassParam(Class aClass) {
						if (String.class.equals(aClass))
							return true;

						return false;
					}
				};
			}
		};

		// When & Then
		assertTrue(mockedObject.methodWithClassParam(String.class));
		assertFalse(mockedObject.methodWithClassParam(Integer.class));
	}

	/**
	 * This is not JMockit related but handy for general testing.
	 * 
	 * Spring offers us Reflection utils for testing. This is handy for
	 * injecting values into members without having to add setters that is only
	 * used by tests; besides, sometimes one does not want to expose a member in
	 * which case a setter is actually decreasing the safety of code.
	 * 
	 * Note: I'm not personally hugely in favor of using reflections like this
	 * since it makes refactoring of code more difficult since fields are
	 * specified by a String literal which is not picked-up as a java-usage. But
	 * its the better of 2 evils.
	 */
	public void testReflection() {
		// Given
		String originalPrivateString = "a final string";
		DomainClassToInstantiate realInstance = new DomainClassToInstantiate(originalPrivateString);

		assertEquals(originalPrivateString, realInstance.getPrivateField());

		// When
		String newPrivateString = "accessible via reflection";
		ReflectionTestUtils.setField(realInstance, "privateField",
				newPrivateString);
		// Then
		assertEquals(newPrivateString, realInstance.getPrivateField());
	}
}
