package com.saniyat.patientservice.controller;

import com.saniyat.patientservice.dto.PatientRequestDTO;
import com.saniyat.patientservice.dto.PatientResponseDTO;
import com.saniyat.patientservice.dto.validators.CreatePatientValidationGroup;
import com.saniyat.patientservice.service.PatientService;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
	private final PatientService patientService;

	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping
	public ResponseEntity<List<PatientResponseDTO>> getPatients() {
		List<PatientResponseDTO> patients = patientService.getAllPatients();
		return ResponseEntity.ok(patients);
	}

	@PostMapping
	public ResponseEntity<PatientResponseDTO> createPatient(
			@Validated({Default.class, CreatePatientValidationGroup.class})
			@RequestBody PatientRequestDTO patientRequestDTO) {
		PatientResponseDTO createdPatient = patientService.createPatient(patientRequestDTO);
		return ResponseEntity.ok(createdPatient);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable("id") UUID id,
			@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
		PatientResponseDTO updatedPatient = patientService.updatePatient(patientRequestDTO, id);
		return ResponseEntity.ok(updatedPatient);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable("id") UUID id) {
		patientService.deletePatient(id);
		return ResponseEntity.noContent().build();
	}
}
