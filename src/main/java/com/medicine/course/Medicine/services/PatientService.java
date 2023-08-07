package com.medicine.course.Medicine.services;

import com.medicine.course.Medicine.entities.Patient;
import com.medicine.course.Medicine.exception.ObjectNotFoundException;
import com.medicine.course.Medicine.models.request.Address;
import com.medicine.course.Medicine.models.request.PatientDto;
import com.medicine.course.Medicine.models.response.PatientResponse;
import com.medicine.course.Medicine.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.medicine.course.Medicine.util.HelpersUtils.configureAddressDto;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    public Patient registerPatient(PatientDto patientDto) {
        try {
            Patient patient = new Patient();

            patient.setName(patientDto.getName());
            patient.setPhone(patientDto.getPhone());
            patient.setEmail(patientDto.getEmail());
            patient.setCpf(patientDto.getCpf());
            patient.setActive(true);

            Address address = configureAddressDto(patientDto.getAddress(), new Address());
            patient.setAddress(address);

            return patientRepository.save(patient);

        } catch (ObjectNotFoundException e) {
            logger.error("Error saving patient: {}", e.getMessage());
            throw e;
        }
    }

    public PatientResponse updatePatient(Long id, PatientDto patientDto) {
        try {
            Patient existingPatient = findPatientById(id);

            existingPatient.setName(patientDto.getName());
            existingPatient.setPhone(patientDto.getPhone());
            existingPatient.setEmail(patientDto.getEmail());
            existingPatient.setCpf(patientDto.getCpf());

            Address address = configureAddressDto(patientDto.getAddress(), new Address());
            existingPatient.setAddress(address);
            existingPatient = patientRepository.save(existingPatient);
            return new PatientResponse("Patient updated successfully, with name: ", existingPatient.getName());
        } catch (ObjectNotFoundException e) {
            logger.error("Error to update patient: {}", e.getMessage());
            throw e;
        }
    }

    public Patient findById(Long id) {
        try {
            Patient patient = findPatientById(id);
            return patient;
        } catch (ObjectNotFoundException e) {
            logger.error("Error to find patient: {}", e.getMessage());
            throw new ObjectNotFoundException("Error to find patient", "id");
        }
    }

    public void deletePatient(Long id) {
        try {
            Patient existingPatient = findPatientById(id);
            patientRepository.delete(existingPatient);
        } catch (ObjectNotFoundException e) {
            logger.error("Error deleting patient: {}", e.getMessage());
            throw new ObjectNotFoundException("Patient not found", "id");
        }
    }

    public List<Patient> listAllPatient() {
        return patientRepository.findAll();
    }

    public Page<Patient> listPageablePatient(@PageableDefault (size = 15, sort = {"name"}) Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    private Patient findPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Patient not found", "id"));
    }
}
