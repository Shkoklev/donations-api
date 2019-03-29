package com.mk.donations.repository.converters;

import com.mk.donations.model.Quantity;
import com.mk.donations.model.factory.QuantityFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class QuantityConverter implements AttributeConverter<Quantity, String> {

    @Override
    public String convertToDatabaseColumn(Quantity quantity) {
        return Optional.ofNullable(quantity)
                .map(Quantity::columnSerialize)
                .orElse(null);
    }

    @Override
    public Quantity convertToEntityAttribute(String column) {
        return Optional.ofNullable(column)
                .map(QuantityFactory::parse)
                .orElse(null);
    }
}
