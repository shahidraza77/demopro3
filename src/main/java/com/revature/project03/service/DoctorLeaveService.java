package com.revature.project03.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project03.entities.DoctorLeave;
import com.revature.project03.repository.DoctorLeaveRepository;

@Service
public class DoctorLeaveService {
	
	@Autowired
    private DoctorLeaveRepository repository;
	
	public DoctorLeave saveDoctorLeave(DoctorLeave doctorLeave) {
    	if(repository.existsByLeaveDateAndDoctorId(doctorLeave.getLeaveDate(),doctorLeave.getDoctorId())) {
    		DoctorLeave dl = new DoctorLeave();
    		dl.setStatus("declined");
    		return dl;
    	}
    	else {
    		doctorLeave.setStatus("confirmed");
    		return repository.save(doctorLeave);
    	}
       
    }
	
	public String deleteDoctorLeave(int id) {
        repository.deleteByDoctorId(id);
        return "Doctor Leave removed !! " + id;
    }
	public List<DoctorLeave> findAllLeaves(){
		return repository.findAll();
	}
	
	public List<DoctorLeave> findAllLeavesOfDoctorById(int id){
		return repository.findAllByDoctorId(id);
		
	}
	public List<DoctorLeave> findbydates(Date date) {
		return repository.findAllByLeaveDate(date);
	}

}
