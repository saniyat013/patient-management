package com.saniyat.patientservice.service;

import com.saniyat.patientservice.dto.PatientRequestDTO;
import com.saniyat.patientservice.dto.PatientResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PatientService {
	public List<PatientResponseDTO> getAllPatients();
	public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);
	public PatientResponseDTO updatePatient(PatientRequestDTO patientRequestDTO, UUID id);
	public void deletePatient(UUID id);
}
