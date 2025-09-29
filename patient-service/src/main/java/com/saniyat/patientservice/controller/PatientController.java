package com.saniyat.patientservice.controller;

import com.saniyat.patientservice.dto.PatientRequestDTO;
import com.saniyat.patientservice.dto.PatientResponseDTO;
import com.saniyat.patientservice.dto.validators.CreatePatientValidationGroup;
import com.saniyat.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Patient", description = "APIs for managing patients")
public class PatientController {
	private final PatientService patientService;

	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping
	@Operation(summary = "Get All Patients", description = "Retrieve a list of all patients")
	public ResponseEntity<List<PatientResponseDTO>> getPatients() {
		List<PatientResponseDTO> patients = patientService.getAllPatients();
		return ResponseEntity.ok(patients);
	}

	@PostMapping
	@Operation(summary = "Create Patient", description = "Create a new patient")
	public ResponseEntity<PatientResponseDTO> createPatient(
			@Validated({Default.class, CreatePatientValidationGroup.class})
			@RequestBody PatientRequestDTO patientRequestDTO) {
		PatientResponseDTO createdPatient = patientService.createPatient(patientRequestDTO);
		return ResponseEntity.ok(createdPatient);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update Patient", description = "Update an existing patient by ID")
	public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable("id") UUID id,
			@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
		PatientResponseDTO updatedPatient = patientService.updatePatient(patientRequestDTO, id);
		return ResponseEntity.ok(updatedPatient);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Patient", description = "Delete a patient by ID")
	public ResponseEntity<Void> deletePatient(@PathVariable("id") UUID id) {
		patientService.deletePatient(id);
		return ResponseEntity.noContent().build();
	}
}
