package com.medicine.course.Medicine.entities;

import com.medicine.course.Medicine.enums.Laboratory;
import com.medicine.course.Medicine.enums.Way;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REMEDIO")
public class Medicine {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String name;

    @Column(name = "VIA")
    @Enumerated(EnumType.STRING)
    private Way way;

    @Column(name = "LOTE")
    private String batch;

    @Column(name = "QUANTIDADE")
    private Integer quantity;

    @Column(name = "ATIVO")
    private Boolean active;

    @Column(name = "VALIDADE")
    private LocalDate validity;

    @Column(name = "LABORATORIO")
    @Enumerated(EnumType.STRING)
    private Laboratory laboratory;

}
