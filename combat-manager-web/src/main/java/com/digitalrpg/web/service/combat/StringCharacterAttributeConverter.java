package com.digitalrpg.web.service.combat;

public class StringCharacterAttributeConverter implements
		CharacterAttributeConverter<String> {

	public String convert(String input) {
		return input;
	}

	public Class<String> getSupportedType() {
		return String.class;
	}

}
