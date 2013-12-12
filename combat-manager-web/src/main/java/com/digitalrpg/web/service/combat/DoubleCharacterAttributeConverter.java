package com.digitalrpg.web.service.combat;

import org.apache.commons.lang3.math.NumberUtils;

public class DoubleCharacterAttributeConverter implements
		CharacterAttributeConverter<Double> {

	public Double convert(String input) {
		return NumberUtils.toDouble(input);
	}

	public Class<Double> getSupportedType() {
		return Double.class;
	}

}
