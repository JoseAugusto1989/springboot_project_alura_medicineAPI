package com.medicine.course.Medicine.models.request;

import com.medicine.course.Medicine.enums.State;
import com.medicine.course.Medicine.util.Regex;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Embeddable
public class Address {

    @NotBlank
    @Column(name = "CEP")
    @Pattern(regexp = Regex.CEP_MASK, message = "Invalid CEP. Please use the format XXXXX-XXX")
    private String postalCode;

    @Column(name = "ESTADO")
    @Enumerated(EnumType.STRING)
    private State uf;

    @NotBlank
    @Column(name = "CIDADE")
    private String city;

    @NotBlank
    @Column(name = "LOGRADOURO")
    private String street;

    @NotBlank
    @Column(name = "BAIRRO")
    private String district;

    @Column(name = "COMPLEMENTO")
    private String complement;

    @Column(name = "NUMERO")
    private String number;
}
