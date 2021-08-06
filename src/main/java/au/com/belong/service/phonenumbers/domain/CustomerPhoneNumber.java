package au.com.belong.service.phonenumbers.domain;

import au.com.belong.service.phonenumbers.exception.CanntActivateActivePhoneNumberException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerPhoneNumber {

    private long id;

    private long customerId;

    private String phoneNumber;

    private boolean isActive;

    public void activate() {
        if (isActive) {
            throw new CanntActivateActivePhoneNumberException(String.format("Phone number %s has already by activated", phoneNumber));
        }
        this.isActive = true;
    }
}
