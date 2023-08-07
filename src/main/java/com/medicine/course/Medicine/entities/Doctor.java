package com.medicine.course.Medicine.entities;

import com.medicine.course.Medicine.models.request.Address;
import com.medicine.course.Medicine.enums.MedicalSpeciality;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEDICO")
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TELEFONE")
    private String phone;

    @Column(name = "CRM")
    private String crm;

    @Column(name = "ESPECIALIDADE")
    @Enumerated(EnumType.STRING)
    private MedicalSpeciality speciality;

    @NotNull
    @Column(name = "ATIVO")
    private Boolean active;

    @Embedded
    private Address address;
}
