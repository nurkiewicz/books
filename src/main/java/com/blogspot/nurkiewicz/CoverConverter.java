package com.blogspot.nurkiewicz;

import com.blogspot.nurkiewicz.rest.Cover;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static com.blogspot.nurkiewicz.rest.Cover.*;

@Converter
public class CoverConverter implements AttributeConverter<Cover, String> {

	@Override
	public String convertToDatabaseColumn(Cover attribute) {
		switch (attribute) {
			case DUST_JACKET:
				return "D";
			case HARDCOVER:
				return "H";
			case PAPERBACK:
				return "P";
			default:
				throw new IllegalStateException("Unknown" + attribute);
		}
	}

	@Override
	public Cover convertToEntityAttribute(String dbData) {
		switch (dbData) {
			case "D":
				return DUST_JACKET;
			case "H":
				return HARDCOVER;
			case "P":
				return PAPERBACK;
			default:
				throw new IllegalStateException("Unknown" + dbData);
		}
	}
}