package com.medicine.course.Medicine.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardError {

    private String message;
    private String field;
    private Date date;
}
