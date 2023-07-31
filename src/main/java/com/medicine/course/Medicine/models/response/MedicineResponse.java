package com.medicine.course.Medicine.models.response;

import com.medicine.course.Medicine.models.Laboratory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineResponse {

    private String message;
    private String name;
    private Laboratory laboratory;
    private Boolean active;
}
