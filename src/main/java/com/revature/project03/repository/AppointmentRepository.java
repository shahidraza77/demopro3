package com.revature.project03.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.project03.entities.Appointment;
import com.revature.project03.entities.Doctor;
import com.revature.project03.entities.Patient;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{
	
	public List<Appointment> findAllByapplicationDateBetween(Date startDate, Date endDate);
	boolean existsByApplicationDateAndPatient(Date date,Patient patient);
	boolean existsByApplicationDateAndDoctor(Date date,Doctor doctor);
}