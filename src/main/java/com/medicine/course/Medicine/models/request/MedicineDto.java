package com.medicine.course.Medicine.models.request;

import com.medicine.course.Medicine.models.Laboratory;
import com.medicine.course.Medicine.models.Way;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineDto {

    @NotBlank
    private String name;

    @Enumerated
    private Way way;

    @NotBlank
    private String batch;

    private Integer quantity;

    private Boolean active;

    @Future
    private LocalDate validity;

    @Enumerated
    private Laboratory laboratory;
}
