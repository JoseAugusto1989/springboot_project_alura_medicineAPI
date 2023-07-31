package com.medicine.course.Medicine.util;

public interface Regex {

    String CEP_REGEX = "[\\d]{8}";
    String CEP_MASK =  "[\\d]{5}-[\\d]{3}";
    String CPF_REGEX = "[\\d]{11}";
    String CPF_REGEX_WITH_MASK = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
    String PHONE_REGEX_WITH_MASK = "\\(\\d{2}\\) \\d{4,5}-\\d{4}";
    String CRM_BRAZIL_REGEX = "^[A-Z]{2}\\d{4,5}$";
}
