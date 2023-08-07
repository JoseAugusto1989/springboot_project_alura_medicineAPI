package com.medicine.course.Medicine.models.request;

import com.medicine.course.Medicine.util.Regex;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String login;

    @NotBlank
    @Size(min = 6, max = 30, message = "The password must be between 6 and 30 characters.")
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank(message = "The 'cpf' field is required")
    @Pattern(regexp = Regex.CPF_REGEX_WITH_MASK, message = "Invalid CPF format. Please use the format XXX.XXX.XXX-XX.")
    private String cpf;

    @NotNull
    @Valid
    private Address address;
}
