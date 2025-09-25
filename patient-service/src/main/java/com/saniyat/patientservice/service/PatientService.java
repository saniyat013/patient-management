package com.saniyat.patientservice.service;

import com.saniyat.patientservice.dto.PatientResponseDTO;

import java.util.List;

public interface PatientService {
    public List<PatientResponseDTO> getAllPatients();
}
