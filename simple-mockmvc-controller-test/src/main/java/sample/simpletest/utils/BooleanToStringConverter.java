package sample.simpletest.utils;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Slf4j
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        // Entity -> DB
        String convertString = String.valueOf(attribute);
        log.info("Entity -> DB:: old = {}, new = {}", attribute, convertString);

        return attribute == true ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        // DB -> Entity
        Boolean convertBoolean = Boolean.valueOf(dbData);
        log.info("DB -> Entity:: old = {}, new = {}", dbData, convertBoolean);

        return dbData.equals("Y") ? true : false;
    }
}
