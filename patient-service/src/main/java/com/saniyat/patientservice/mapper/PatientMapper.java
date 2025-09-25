package com.saniyat.patientservice.mapper;

import com.saniyat.patientservice.dto.PatientRequestDTO;
import com.saniyat.patientservice.dto.PatientResponseDTO;
import com.saniyat.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
	public static PatientResponseDTO toDTO(Patient patient) {
		PatientResponseDTO patientDto = new PatientResponseDTO();
		patientDto.setId(patient.getId().toString());
		patientDto.setName(patient.getName());
		patientDto.setEmail(patient.getEmail());
		patientDto.setAddress(patient.getAddress());
		patientDto.setDateOfBirth(patient.getDateOfBirth().toString());
		return patientDto;
	}

	public static Patient toModel(PatientRequestDTO patientRequestDTO) {
		Patient patient = new Patient();
		patient.setName(patientRequestDTO.getName());
		patient.setEmail(patientRequestDTO.getEmail());
		patient.setAddress(patientRequestDTO.getAddress());
		patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
		patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
		return patient;
	}
}
