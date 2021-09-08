package com.revature.project03.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project03.entities.Appointment;
import com.revature.project03.exception.ResourceNotFoundException;
import com.revature.project03.service.AppointmentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
    
	@PostMapping("/book/{patientid}")
	public Appointment createAppointment(@RequestBody Appointment appointment, @PathVariable(value = "patientid") Integer patientId) throws ResourceNotFoundException{
		log.trace("Appointment has booked with patient id ");
		
		return appointmentService.createAppointment(appointment, patientId);
	}
	
	@GetMapping("/patient/{patientid}/getappointment")
	public List<Appointment> getAppointmentByPatientId(@PathVariable(value = "patientid") Integer patientId) throws ResourceNotFoundException{
		log.trace(" getting Appointment by patient id ");
		return appointmentService.getAppointmentByPatientId(patientId);
	}
	
	@GetMapping("/member/{familyid}/getappointment")
	public List<Appointment> getAppointmentByFamilyId(@PathVariable(value = "familyid") Integer familyId) throws ResourceNotFoundException{
		log.trace("getting Appointment by patient id ");
		return appointmentService.getAppointmentByFamilyId(familyId);
	}
	
	@GetMapping("/getappointmentbydate")
	public List<Appointment> getAppointmentByDate(@RequestParam Date date) throws ResourceNotFoundException{
		return appointmentService.getAppointmentByDate(date);
	}
}