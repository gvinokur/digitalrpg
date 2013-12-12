package com.digitalrpg.web.service.combat;

import org.apache.commons.lang3.math.NumberUtils;

public class LongCharacterAttributeConverter implements
		CharacterAttributeConverter<Long> {

	public Long convert(String input) {
		return NumberUtils.toLong(input);
	}

	public Class<Long> getSupportedType() {
		return Long.class;
	}

}
