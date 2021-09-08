package com.revature.project03;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project03.controller.AppointmentController;
import com.revature.project03.controller.PatientController;
import com.revature.project03.controller.ReceptionistController;
import com.revature.project03.entities.Appointment;
import com.revature.project03.entities.Doctor;
import com.revature.project03.entities.Family;
import com.revature.project03.entities.Receptionist;
import com.revature.project03.service.AppointmentService;
import com.revature.project03.service.PatientService;
import com.revature.project03.service.ReceptionistService;

class ReceptionistControllerTest {

	@InjectMocks
	ReceptionistController rceptionistController;
	
	//@InjectMocks
	//AppointmentController appointmentController;
	
	@Mock
	 ReceptionistService receptionistService;
	
	//@Mock
	//AppointmentService appointmentService;
	
	private MockMvc mockAppointmentMvc, mockMvc;
	private ObjectMapper mapper;
	
	@BeforeEach
	public void init() {
		mockAppointmentMvc = MockMvcBuilders.standaloneSetup(receptionistService).build();
		//mockMvc = MockMvcBuilders.standaloneSetup(RceptionistController).build();
		mapper = new ObjectMapper();
	}

	@Test
	public void testAddMembers() throws Exception {
		Receptionist recp=new Receptionist();
		recp.setREmail("hema@gmail.com");
		recp.setRId(10);
		recp.setRName("hema");
		recp.setRPassword("d6H%%v3KLy");
		recp.setRPhnNo(8989878987L);
		
//		Family member=new Family();
//		member.setFamily_id(4);
//		member.setFirstName("Tester");
		
		Mockito.when(
				receptionistService.saveReceptionist(Mockito.any(Receptionist.class))
		).thenReturn(recp);

		String postData = "{"
				+ "    \"rId\": 10,"
				+ "    \"rName\": \"hema\","
				+ "    \"rPhnNo\": \"8989878987\","
				+ "    \"rEmail\": \"hema@gmail.com\","
				+ "}";
		
		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/receptionistController/addReceptionist");
				//.accept(MediaType.APPLICATION_JSON).content(postData)
				//.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Receptionist actualResponse = mapper.readValue(result.getResponse().getContentAsString(), Receptionist.class);
//		Mockito.when(
//				receptionistService.saveReceptionist(Mockito.any(Receptionist.class))
//		).thenReturn(recp);
		assertEquals(recp, actualResponse);
	}
	
	
}
//package com.revature.project03;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.revature.project03.controller.AppointmentController;
//import com.revature.project03.controller.DoctorController;
//import com.revature.project03.controller.PatientController;
//import com.revature.project03.entities.Appointment;
//import com.revature.project03.entities.Doctor;
//import com.revature.project03.entities.Family;
//import com.revature.project03.entities.Patient;
//import com.revature.project03.service.AppointmentService;
//import com.revature.project03.service.DoctorService;
//import com.revature.project03.service.PatientService;
//
//@ExtendWith(MockitoExtension.class)
//public class DoctorControllerTest{
//
//	@InjectMocks
//	DoctorController doctorController;
//	
//	//@InjectMocks
//	//AdminController adminController;
//	
//	@Mock
//	DoctorService doctorService;
//	
//	//@Mock
//	//AppointmentService appointmentService;
//	
//	private MockMvc mockDoctorMvc, mockMvc;
//	private ObjectMapper mapper;
//	
//	@BeforeEach
//	public void init() {
//		mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
//		//mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
//		mapper = new ObjectMapper();
//	}
//
//	@Test
//	public void testGetDoctors() throws Exception {
//		
//		Doctor member=new Doctor();
//		member.setDoctorId(4);
//		member.setFirstName("Tester");
//		
//		
//		Mockito.when(
//			doctorService.getDoctorById(Mockito.anyInt())
//		).thenReturn(member);
//
////		String postData = "{"
////				+ "    \"doctorId\": 2,"
////				+ "    \"firstName\": \"Sree\","
////				+ "    \"lastName\": \"K\","
////				+ "    \"email\": 21,"
////				+ "    \"phno\": null,"
////				+ "    \"fees\": \"8529638527\","
////				+ "    \"designation\": \"Hyderabad\","
////				+ "    \"specialization\": \"sree@gmail.com\","
////				+ "		\"doctorAvailability\":pending \","
////				+ "		\"password\":sree123 \","
////				+ "}";
//		
		// Send course as body to /students/Student1/courses
		//RequestBuilder requestBuilder = MockMvcRequestBuilders
				//.get("/doctorController/doctorById/14");
//				.accept(MediaType.APPLICATION_JSON).content(getData)
//				.contentType(MediaType.APPLICATION_JSON);

		//MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		//Doctor actualResponse = mapper.readValue(result.getResponse().getContentAsString(), Doctor.class);
		//assertEquals(member, actualResponse);
	//}
	
//}
