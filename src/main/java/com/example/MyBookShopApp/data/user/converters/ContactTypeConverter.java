package com.example.MyBookShopApp.data.user.converters;

import com.example.MyBookShopApp.data.enums.ContactType;

import javax.persistence.AttributeConverter;

public class ContactTypeConverter implements AttributeConverter<ContactType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ContactType contactType) {
        switch (contactType){
            case EMAIL:
                return 1;
            case PHONE:
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public ContactType convertToEntityAttribute(Integer integer) {
        switch (integer){
            case 1:
                return ContactType.EMAIL;
            case 2:
                return ContactType.PHONE;
            default:
                return null;
        }
    }
}
