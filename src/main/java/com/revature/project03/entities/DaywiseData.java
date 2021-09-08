package com.revature.project03.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.project03.model.DoctorAvailability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Day_Wise_Data_Table")
public class DaywiseData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int dataId;
	private Date date;
	private int docId;
	private String docName;
	private int totalPatients;
	private int amountCollected;

}
