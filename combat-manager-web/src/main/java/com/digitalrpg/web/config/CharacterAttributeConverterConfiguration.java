package com.digitalrpg.web.config;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.digitalrpg.web.service.combat.BooleanCharacterAttributeConverter;
import com.digitalrpg.web.service.combat.CharacterAttributeConverter;
import com.digitalrpg.web.service.combat.DoubleCharacterAttributeConverter;
import com.digitalrpg.web.service.combat.IntegerCharacterAttributeConverter;
import com.digitalrpg.web.service.combat.LongCharacterAttributeConverter;
import com.digitalrpg.web.service.combat.PathfinderActionCharacterAttributeConverter;
import com.digitalrpg.web.service.combat.StringCharacterAttributeConverter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

@Configuration
@Order(1)
public class CharacterAttributeConverterConfiguration {

	@Bean 
	public CharacterAttributeConverter getStringCharacterAttributeConverter() {
		return new StringCharacterAttributeConverter();
	}
	@Bean 
	public CharacterAttributeConverter getBooleanCharacterAttributeConverter() {
		return new BooleanCharacterAttributeConverter();
	}
	@Bean 
	public CharacterAttributeConverter getIntegerCharacterAttributeConverter() {
		return new IntegerCharacterAttributeConverter();
	}
	@Bean 
	public CharacterAttributeConverter getLongCharacterAttributeConverter() {
		return new LongCharacterAttributeConverter();
	}
	@Bean 
	public CharacterAttributeConverter getDoubleCharacterAttributeConverter() {
		return new DoubleCharacterAttributeConverter();
	}
	@Bean
	public CharacterAttributeConverter getPathfinderActionCharacterAttributeConverter() {
		return new PathfinderActionCharacterAttributeConverter();
	}
	@SuppressWarnings("rawtypes")
	@Bean(name = "characterAttributeConvertersMap")
	public Map<Class, CharacterAttributeConverter> getCharacterAttributeConverterMap(ApplicationContext ctx) {
		Map<String, CharacterAttributeConverter> converters = ctx.getBeansOfType(CharacterAttributeConverter.class);
		Builder<Class, CharacterAttributeConverter> builder = ImmutableMap.<Class, CharacterAttributeConverter>builder();
		for (CharacterAttributeConverter converter : converters.values()) {
			builder.put(converter.getSupportedType(), converter);
		}
		return builder.build();
	}
}
