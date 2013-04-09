package domain;

public class DomainClassToInstantiate {

	private final String privateField;

	public DomainClassToInstantiate(String value) {
		privateField = value;
	}

	public String getPrivateField() {
		return privateField;
	}
}
