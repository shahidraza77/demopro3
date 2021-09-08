package com.revature.project03.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.project03.entities.DaywiseData;

public interface DayWiseDataRepository extends JpaRepository<DaywiseData, Integer>{
	
	List<DaywiseData> findAllByDate(Date date);
	boolean existsByDateAndDocId(Date date, int docId);
	DaywiseData findByDateAndDocId(Date date, int docId);

}
