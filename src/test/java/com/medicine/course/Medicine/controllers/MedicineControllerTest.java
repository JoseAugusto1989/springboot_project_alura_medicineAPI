package com.medicine.course.Medicine.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicine.course.Medicine.models.Laboratory;
import com.medicine.course.Medicine.models.request.MedicineDto;
import com.medicine.course.Medicine.models.Way;
import com.medicine.course.Medicine.services.MedicineService;
import com.medicine.course.Medicine.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicineController.class)
public class MedicineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MedicineService medicineService;

    @Mock
    private DateUtil dateUtil;

    @InjectMocks
    private MedicineController medicineController;

    private static final String URI_PATH = "/medicine";

    @Test
    public void testRegisterMedicine_whenValidMedicineDto_shouldReturnSuccess() throws Exception {
        MedicineDto medicineDto = createMedicineDto();

        mockMvc.perform(post(URI_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .content(asJsonString(medicineDto)))
                .andExpect(status().isOk());
    }

    private MedicineDto createMedicineDto() {
        MedicineDto medicineDto = new MedicineDto();
        medicineDto.setName("Medicine test");
        medicineDto.setWay(Way.ORAL);
        medicineDto.setQuantity(100);
        medicineDto.setActive(true);
        medicineDto.setBatch("123LBO987");
        medicineDto.setLaboratory(Laboratory.CIMED);
        medicineDto.setValidity(LocalDate.parse("2025-08-08"));
        return medicineDto;
    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
