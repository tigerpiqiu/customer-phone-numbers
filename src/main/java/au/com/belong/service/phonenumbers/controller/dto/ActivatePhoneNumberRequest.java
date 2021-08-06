package au.com.belong.service.phonenumbers.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ActivatePhoneNumberRequest {

    @NotBlank(message = "Phone number is mandatory")
    @ApiModelProperty(example = "0303 987 786")
    private String phoneNumber;

}
