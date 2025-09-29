package com.saniyat.patientservice.service.impl;

import com.saniyat.patientservice.dto.PatientRequestDTO;
import com.saniyat.patientservice.dto.PatientResponseDTO;
import com.saniyat.patientservice.exception.EmailAlreadyExistsException;
import com.saniyat.patientservice.exception.PatientNotFoundException;
import com.saniyat.patientservice.mapper.PatientMapper;
import com.saniyat.patientservice.model.Patient;
import com.saniyat.patientservice.repository.PatientRepository;
import com.saniyat.patientservice.service.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

	@Override
	public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
		if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
			throw new EmailAlreadyExistsException("Email " + patientRequestDTO.getEmail() + " already exists");
		}

		Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
		return PatientMapper.toDTO(newPatient);
	}

	@Override
	public PatientResponseDTO updatePatient(PatientRequestDTO patientRequestDTO, UUID id) {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));

		if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
			throw new EmailAlreadyExistsException("Email " + patientRequestDTO.getEmail() + " already exists");
		}

		patient.setName(patientRequestDTO.getName());
		patient.setEmail(patientRequestDTO.getEmail());
		patient.setAddress(patientRequestDTO.getAddress());
		patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
		Patient updatedPatient = patientRepository.save(patient);
		return PatientMapper.toDTO(updatedPatient);
	}

	@Override
	public void deletePatient(UUID id) {
		patientRepository.deleteById(id);
	}
}
