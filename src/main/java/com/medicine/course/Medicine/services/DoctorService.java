package com.medicine.course.Medicine.services;

import com.medicine.course.Medicine.entities.Doctor;
import com.medicine.course.Medicine.exception.ObjectNotFoundException;
import com.medicine.course.Medicine.models.request.Address;
import com.medicine.course.Medicine.models.request.DoctorDto;
import com.medicine.course.Medicine.models.request.UpdateDoctor;
import com.medicine.course.Medicine.models.response.DoctorResponse;
import com.medicine.course.Medicine.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.medicine.course.Medicine.util.HelpersUtils.configureAddressDto;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);

    public Doctor registerDoctor(DoctorDto doctorDto) {
        try {
            Doctor doctor = new Doctor();
            doctor.setName(doctorDto.getName());
            doctor.setEmail(doctorDto.getEmail());
            doctor.setCrm(doctorDto.getCrm());
            doctor.setSpeciality(doctorDto.getSpeciality());
            doctor.setPhone(doctorDto.getPhone());
            doctor.setActive(true);

            Address address = configureAddressDto(doctorDto.getAddress(), new Address());
            doctor.setAddress(address);
            doctor = doctorRepository.save(doctor);
            return doctor;
        } catch (ObjectNotFoundException e) {
            logger.error("Error saving doctor: {}", e.getMessage());
            throw e;
        }
    }

    public DoctorResponse updateDoctor(Long id, UpdateDoctor updateDoctor) {
        try {
            Doctor existingDoctor = findDoctorById(id);

            existingDoctor.setName(updateDoctor.getName());
            existingDoctor.setPhone(updateDoctor.getPhone());
            existingDoctor.setEmail(updateDoctor.getEmail());

            configureAddressDto(updateDoctor.getAddress(), existingDoctor.getAddress());

            existingDoctor = doctorRepository.save(existingDoctor);
            return new DoctorResponse("Doctor updated successfully, with name: ", existingDoctor.getName());

        } catch (ObjectNotFoundException e) {
            logger.error("Error to updating doctor: {}", e.getMessage());
            throw e;
        }
    }

    public void deleteDoctor(Long id) {
        try {
            Doctor existingDoctor = findDoctorById(id);
            doctorRepository.delete(existingDoctor);
        } catch (ObjectNotFoundException e) {
            logger.error("Error deleting doctor: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found", e);
        }
    }

    public void updateDoctorStatus(Long id, boolean isActive) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);

        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setActive(isActive);
            doctorRepository.save(doctor);
        }
        throw new ObjectNotFoundException("Doctor not found", "id");

    }

    public List<Doctor> listAllDoctor() {
        return doctorRepository.findAll();
    }

    public Doctor findById(Long id) {
        try {
            Doctor doctor = findDoctorById(id);
            return doctor;
        } catch (ObjectNotFoundException e) {
            logger.error("Error to find doctor: {}", e.getMessage());
            throw new ObjectNotFoundException("Doctor not found", "id");
        }
    }

    public Page<Doctor> listPageableDoctor(@PageableDefault(size = 15, sort = {"name"})
                                           Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }

    private Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Doctor not found", "id"));
    }
}
