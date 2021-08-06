package au.com.belong.service.phonenumbers.service;

import au.com.belong.service.phonenumbers.domain.CustomerPhoneNumber;
import au.com.belong.service.phonenumbers.persistence.CustomerPhoneNumberDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerPhoneNumberService {

    private CustomerPhoneNumberDAO dao;

    public List<CustomerPhoneNumber> getAllPhoneNumbers() {
        return dao.getAllPhoneNumbers();
    }

    public List<CustomerPhoneNumber> getPhoneNumbersByCustomerId(long customerId) {
        return dao.getPhoneNumbersByCustomerId(customerId);
    }

    public void activatePhoneNumber(String phoneNumber) {
        CustomerPhoneNumber customerPhoneNumber = dao.getByPhoneNumber(phoneNumber);
        customerPhoneNumber.activate();
        dao.update(customerPhoneNumber);
    }
}
