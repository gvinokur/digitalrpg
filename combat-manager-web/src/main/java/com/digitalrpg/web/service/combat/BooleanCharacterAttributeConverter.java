package com.digitalrpg.web.service.combat;

import org.apache.commons.lang3.BooleanUtils;

public class BooleanCharacterAttributeConverter implements
		CharacterAttributeConverter<Boolean> {

	public Boolean convert(String input) {
		return BooleanUtils.toBooleanObject(input);
	}

	public Class<Boolean> getSupportedType() {
		return Boolean.class;
	}

}
