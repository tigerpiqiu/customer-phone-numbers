package au.com.belong.service.phonenumbers.controller.transformer;

import au.com.belong.service.phonenumbers.controller.dto.CustomerPhoneNumberResponse;
import au.com.belong.service.phonenumbers.domain.CustomerPhoneNumber;

public class CustomerPhoneNumberTransformer {

    public CustomerPhoneNumberResponse transform(CustomerPhoneNumber customerPhoneNumber) {
        return CustomerPhoneNumberResponse.builder()
                .customerId(customerPhoneNumber.getCustomerId())
                .phoneNumber(customerPhoneNumber.getPhoneNumber())
                .isActive(customerPhoneNumber.isActive())
                .build();
    }
}
