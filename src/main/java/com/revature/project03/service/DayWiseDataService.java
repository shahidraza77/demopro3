package com.revature.project03.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project03.entities.DaywiseData;
import com.revature.project03.repository.DayWiseDataRepository;

@Service
public class DayWiseDataService {
	@Autowired
	DayWiseDataRepository dayWiseDataRepository;
	
	public DaywiseData addData(DaywiseData data) {
		return dayWiseDataRepository.save(data);
	}
	
	public List<DaywiseData> getData() {
		return dayWiseDataRepository.findAll();
	}
	
	public List<DaywiseData> findbydate(Date date){
		return dayWiseDataRepository.findAllByDate(date);
	}
	
	public boolean checkdata(Date date, int docId) {
		return dayWiseDataRepository.existsByDateAndDocId(date, docId);
	}
	
	public DaywiseData getDataByIdAndDate(Date date,int docId) {
		return dayWiseDataRepository.findByDateAndDocId(date, docId);
	}
	public DaywiseData updateData(DaywiseData data,int amount) {
		DaywiseData dayWise1 = new DaywiseData();
		dayWise1.setDataId(data.getDataId());
		 dayWise1.setDate(data.getDate());
		 dayWise1.setDocId(data.getDocId());
		 dayWise1.setDocName(data.getDocName());
		 dayWise1.setTotalPatients(data.getTotalPatients()+1);
		 dayWise1.setAmountCollected(data.getAmountCollected()+amount);
		 return dayWiseDataRepository.save(dayWise1);
	}

}
