package com.revature.project03.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project03.entities.Prescriptions;

import com.revature.project03.service.PrescriptionService;

import lombok.extern.slf4j.Slf4j;
@RestController 
@Slf4j
@CrossOrigin(origins = "*")
public class PrescriptionController {
	@Autowired
    private PrescriptionService service;

    @PostMapping("/addPrescription")
    public Prescriptions addPrescriptions(@RequestBody Prescriptions prescription) {
    	log.trace("adding Prescription details");
        return service.savePrescriptions(prescription);
    }

    @PostMapping("/addPrescriptions")
    public List<Prescriptions> addPrescriptionss(@RequestBody List<Prescriptions> prescriptions) {
    	log.trace("adding list of  Prescription details");
        return service.savePrescriptionss(prescriptions);
    }

    @GetMapping("/allPrescriptions")
    public List<Prescriptions> findAllPrescriptionss() {
    	log.trace("getting list of Prescription details");
        return service.getPrescriptionss();
    }

    @GetMapping("/prescriptionById/{id}")
    public Prescriptions findPrescriptionsById(@PathVariable int id) {
    	log.trace("getting Prescription detail by prescriptionById");
        return service.getPrescriptionsById(id);
    }
    @GetMapping("/prescriptionByDoctorId/{doctorId}")
    public List<Prescriptions> findPrescriptionsByEmail(@PathVariable int doctorId) {
    	log.trace("getting list of Prescription details by DoctorId");
        return service.getPrescriptionsByDoctorId(doctorId);
    }
    @GetMapping("/allPrescriptionsByPatientId/{patientId}")
    public List<Prescriptions> findAllPrescriptionsByPatientId(@PathVariable int patientId) {
    	log.trace("getting list of Prescription details by PatientId");
        return service.getPrescriptionsByPatientId(patientId);
    }
    
    @PutMapping("/updatePrescriptions")
    public Prescriptions updatePrescriptions(@RequestBody Prescriptions prescription) {
    	log.trace("updating Prescriptions");
        return service.updatePrescriptions(prescription);
    }

    @DeleteMapping("/deletePrescriptions/{id}")
    public String deletePrescriptions(@PathVariable int id) {
    	log.trace("deleting Prescription by id");
        return service.deletePrescriptions(id);
    }
    

}
