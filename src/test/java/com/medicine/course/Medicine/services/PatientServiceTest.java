package com.medicine.course.Medicine.services;

import com.medicine.course.Medicine.entities.Patient;
import com.medicine.course.Medicine.exception.ObjectNotFoundException;
import com.medicine.course.Medicine.models.request.PatientDto;
import com.medicine.course.Medicine.models.response.PatientResponse;
import com.medicine.course.Medicine.repositories.PatientRepository;
import com.medicine.course.Medicine.template.PatientDtoTemplate;
import com.medicine.course.Medicine.template.PatientTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterPatient_WhenPatientExists_ShouldReturnRegisterPatient() {
        PatientDto patientDto = PatientDtoTemplate.buildPatientDto();

        Patient expectedPatient = PatientTemplate.buildPatient();

        when(patientRepository.save(any(Patient.class))).thenReturn(expectedPatient);

        Patient resultPatient = patientService.registerPatient(patientDto);

        assertEquals(expectedPatient, resultPatient);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    public void testUpdatePatient() {
        // Arrange
        Long patientId = 1L;
        PatientDto patientDto = new PatientDto();
        // Set the properties of the patientDto

        Patient existingPatient = new Patient();
        // Set the properties of the existingPatient

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(existingPatient);

        // Act
        PatientResponse resultResponse = patientService.updatePatient(patientId, patientDto);

        // Assert
        assertEquals("Patient updated successfully, with name: " + existingPatient.getName(), resultResponse.getMessage());
    }

    @Test
    public void testFindByIdExistingPatient() {
        // Arrange
        Long patientId = 1L;
        Patient expectedPatient = new Patient();
        // Set the properties of the expectedPatient

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(expectedPatient));

        // Act
        Patient resultPatient = patientService.findById(patientId);

        // Assert
        assertEquals(expectedPatient, resultPatient);
    }

    @Test
    public void testFindByIdNonExistingPatient() {
        // Arrange
        Long patientId = 1L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class, () -> patientService.findById(patientId));
    }

    @Test
    public void testDeletePatient() {
        // Arrange
        Long patientId = 1L;
        Patient existingPatient = new Patient();
        // Set the properties of the existingPatient

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));

        // Act
        patientService.deletePatient(patientId);

        // Assert
        verify(patientRepository, times(1)).delete(existingPatient);
    }
}
