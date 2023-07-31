package com.medicine.course.Medicine.util;

import com.medicine.course.Medicine.models.request.AddressDto;

public class HelpersUtils {

    public static AddressDto configureAddressDto(AddressDto sourceAddressDto, AddressDto targetAddressDto) {
        targetAddressDto.setCity(sourceAddressDto.getCity());
        targetAddressDto.setUf(sourceAddressDto.getUf());
        targetAddressDto.setComplement(sourceAddressDto.getComplement());
        targetAddressDto.setNumber(sourceAddressDto.getNumber());
        targetAddressDto.setDistrict(sourceAddressDto.getDistrict());
        targetAddressDto.setPostalCode(sourceAddressDto.getPostalCode());
        return targetAddressDto;
    }
}
