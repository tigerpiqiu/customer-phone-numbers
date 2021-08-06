package au.com.belong.service.phonenumbers.controller;


import au.com.belong.service.phonenumbers.controller.dto.ActivatePhoneNumberRequest;
import au.com.belong.service.phonenumbers.controller.dto.CustomerPhoneNumberResponse;
import au.com.belong.service.phonenumbers.domain.CustomerPhoneNumber;
import au.com.belong.service.phonenumbers.exception.CanntActivateActivePhoneNumberException;
import au.com.belong.service.phonenumbers.exception.NotFoundException;
import au.com.belong.service.phonenumbers.persistence.CustomerPhoneNumberDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(
        scripts = {
                "/db/schema.sql",
                "/db/data.sql"
        },
        executionPhase = BEFORE_TEST_METHOD
)
@ActiveProfiles("test")
public class CustomerPhoneNumbersControllerIntegrationTest {

    @Autowired
    private CustomerPhoneNumberDAO dao;

    @Autowired
    private CustomerPhoneNumbersController customerPhoneNumbersController;

    @Test
    void shouldRetrieveAllPhoneNumbers() {
        List<CustomerPhoneNumberResponse> allPhoneNumbers = customerPhoneNumbersController.getAllPhoneNumbers();

        assertEquals(4, allPhoneNumbers.size());
        assertPhoneNumberFound(1001, "0303 987 786", true, allPhoneNumbers);
        assertPhoneNumberFound(1002, "0303 345 567", false, allPhoneNumbers);
    }

    @Test
    void shouldRetrievePhoneNumbersByCustomerId() {
        List<CustomerPhoneNumberResponse> phoneNumbersByCustomerId = customerPhoneNumbersController.getPhoneNumbersByCustomerId(1001);

        assertEquals(2, phoneNumbersByCustomerId.size());
        assertPhoneNumberFound(1001, "0303 987 786", true, phoneNumbersByCustomerId);
        assertPhoneNumberFound(1001, "0404 123 345", false, phoneNumbersByCustomerId);
    }

    @Test
    void shouldActivatePhoneNumberWhenItsNotActive() {
        ActivatePhoneNumberRequest request = activatePhoneNumberRequest("0404 123 345");

        customerPhoneNumbersController.activatePhoneNumber(request);

        CustomerPhoneNumber phoneNumber = dao.getByPhoneNumber(request.getPhoneNumber());
        assertTrue(phoneNumber.isActive());
    }

    @Test
    void shouldThrowExceptionWhenAttemptingToActivateActivePhoneNumber() {
        ActivatePhoneNumberRequest request = activatePhoneNumberRequest("0303 987 786");

        CanntActivateActivePhoneNumberException exception = assertThrows(CanntActivateActivePhoneNumberException.class,
                () -> customerPhoneNumbersController.activatePhoneNumber(request));

        assertEquals("Phone number 0303 987 786 has already by activated", exception.getMessage());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenAttemptingToActivateNonexistentPhoneNumber() {
        ActivatePhoneNumberRequest request = activatePhoneNumberRequest("0303 987 xxx");

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> customerPhoneNumbersController.activatePhoneNumber(request));

        assertEquals("Phone number 0303 987 xxx not found", exception.getMessage());
    }

    private ActivatePhoneNumberRequest activatePhoneNumberRequest(String phoneNumber) {
        ActivatePhoneNumberRequest request = new ActivatePhoneNumberRequest();
        request.setPhoneNumber(phoneNumber);
        return request;
    }

    private void assertPhoneNumberFound(long customerId, String phoneNumber, boolean isActive,
                                        List<CustomerPhoneNumberResponse> phoneNumberResponses) {
        Optional<CustomerPhoneNumberResponse> phoneNumberResponse = phoneNumberResponses.stream()
                .filter(p -> p.getPhoneNumber().equals(phoneNumber)).findAny();

        assertTrue(phoneNumberResponse.isPresent());
        assertEquals(customerId, phoneNumberResponse.get().getCustomerId());
        assertEquals(isActive, phoneNumberResponse.get().isActive());
    }
}