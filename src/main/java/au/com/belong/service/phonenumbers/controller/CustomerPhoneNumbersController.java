package au.com.belong.service.phonenumbers.controller;

import au.com.belong.service.phonenumbers.controller.dto.ActivatePhoneNumberRequest;
import au.com.belong.service.phonenumbers.controller.dto.CustomerPhoneNumberResponse;
import au.com.belong.service.phonenumbers.controller.transformer.CustomerPhoneNumberTransformer;
import au.com.belong.service.phonenumbers.domain.CustomerPhoneNumber;
import au.com.belong.service.phonenumbers.service.CustomerPhoneNumberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/")
@Api(tags = {"Customer phone numbers"})
@AllArgsConstructor
public class CustomerPhoneNumbersController {

    private final CustomerPhoneNumberService customerPhoneNumberService;

    private final CustomerPhoneNumberTransformer phoneNumberTransformer = new CustomerPhoneNumberTransformer();

    @GetMapping(
            value = "/all-phone-numbers",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiOperation("Get all customer phone numbers")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Customer phone numbers found")
    })
    public List<CustomerPhoneNumberResponse> getAllPhoneNumbers() {
        return transform(customerPhoneNumberService.getAllPhoneNumbers());
    }

    @GetMapping(
            value = "/{customerId}/phone-numbers",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiOperation("Get phone numbers by customer id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Customer phone numbers found")
    })
    public List<CustomerPhoneNumberResponse> getPhoneNumbersByCustomerId(@PathVariable("customerId") long customerId) {
        return transform(customerPhoneNumberService.getPhoneNumbersByCustomerId(customerId));
    }


    @PutMapping(value = "/phone-number/activate")
    @ApiOperation("Activate a phone number")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Phone number activated"),
            @ApiResponse(code = 404, message = "Phone number not found")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public void activatePhoneNumber(@RequestBody @Valid ActivatePhoneNumberRequest request) {
        customerPhoneNumberService.activatePhoneNumber(request.getPhoneNumber());
    }


    private List<CustomerPhoneNumberResponse> transform(List<CustomerPhoneNumber> phoneNumbers) {
        return phoneNumbers.stream()
                .map(phoneNumberTransformer::transform)
                .collect(Collectors.toList());
    }
}
