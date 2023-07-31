package com.medicine.course.Medicine.models.request;

import com.medicine.course.Medicine.util.Regex;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDoctor {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = Regex.PHONE_REGEX_WITH_MASK, message = "Invalid phone number. Please use the format (XX) XXXXX-XXXX.")
    private String phone;

    @NotBlank
    @Valid
    private AddressDto address;
}
