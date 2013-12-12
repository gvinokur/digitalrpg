package com.digitalrpg.web.service.combat;

import org.apache.commons.lang3.math.NumberUtils;

public class IntegerCharacterAttributeConverter implements
		CharacterAttributeConverter<Integer> {

	public Integer convert(String input) {
		return NumberUtils.toInt(input);
	}

	public Class<Integer> getSupportedType() {
		return Integer.class;
	}

}
