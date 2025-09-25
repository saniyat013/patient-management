package com.saniyat.patientservice.service.impl;

import com.saniyat.patientservice.dto.PatientResponseDTO;
import com.saniyat.patientservice.mapper.PatientMapper;
import com.saniyat.patientservice.model.Patient;
import com.saniyat.patientservice.repository.PatientRepository;
import com.saniyat.patientservice.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();

//        List<PatientResponseDTO> patientResponseDTOS = patients.stream()
//                .map(patient -> PatientMapper.toDTO(patient)).toList();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }
}
