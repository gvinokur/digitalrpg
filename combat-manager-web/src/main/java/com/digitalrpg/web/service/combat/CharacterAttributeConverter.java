package com.digitalrpg.web.service.combat;

public interface CharacterAttributeConverter<T> {
	
	public T convert(String input);
	
	public Class<T> getSupportedType();
}
