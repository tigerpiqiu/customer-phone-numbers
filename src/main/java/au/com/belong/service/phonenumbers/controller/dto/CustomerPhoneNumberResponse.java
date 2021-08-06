package au.com.belong.service.phonenumbers.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerPhoneNumberResponse {

    private Long customerId;

    private String phoneNumber;

    private boolean isActive;
}
