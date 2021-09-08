package com.revature.project03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project03.controller.AppointmentController;
import com.revature.project03.controller.PatientController;
import com.revature.project03.entities.Appointment;
import com.revature.project03.entities.Doctor;
import com.revature.project03.entities.Family;
import com.revature.project03.entities.Patient;
import com.revature.project03.service.AppointmentService;
import com.revature.project03.service.PatientService;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

	@InjectMocks
	PatientController patientController;
	
	@InjectMocks
	AppointmentController appointmentController;
	
	@Mock
	PatientService patientService;
	
	@Mock
	AppointmentService appointmentService;
	
	private MockMvc mockAppointmentMvc, mockMvc;
	private ObjectMapper mapper;
	
	@BeforeEach
	public void init() {
		mockAppointmentMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
		mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
		mapper = new ObjectMapper();
	}

	@Test
	public void testAddMembers() throws Exception {
		
		Family member=new Family();
		member.setFamily_id(4);
		member.setFirstName("Tester");
		
		Mockito.when(
			patientService.createFamilyMember(Mockito.any(Family.class), Mockito.anyInt())
		).thenReturn(member);

		String postData = "{"
				+ "    \"family_id\": 2,"
				+ "    \"firstName\": \"Sree\","
				+ "    \"lastName\": \"K\","
				+ "    \"age\": 21,"
				+ "    \"gender\": null,"
				+ "    \"mobileNo\": \"8529638527\","
				+ "    \"address\": \"Hyderabad\","
				+ "    \"email_id\": \"sree@gmail.com\","
				+ "    \"doctors\": []"
				+ "}";
		
		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/patient/1/family/addmember")
				.accept(MediaType.APPLICATION_JSON).content(postData)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Family actualResponse = mapper.readValue(result.getResponse().getContentAsString(), Family.class);
		assertEquals(member, actualResponse);
	}
	
	@Test
	public void testBookAppointment() throws Exception {
		
		Doctor doctor=new Doctor();
		doctor.setDoctorId(1);
		Appointment appt=new Appointment();
//		Date date=new Date();
//		appt.setApplicationDate(new Date());
		appt.setApplicationId(1);
		appt.setPurpose("Testing appointment");

		appt.setDoctor(doctor);
		Mockito.when(
			appointmentService.createAppointment(Mockito.any(Appointment.class), Mockito.anyInt())
		).thenReturn(appt);
		
		String appData = "{"
				+ "    \"applicationDate\":\"2021-09-06\","
				+ "    \"purpose\":\"Fever\","
				+ "    \"availability\":\"pending\","
				+ "    \"doctor\":{"
				+ "        \"doctorId\":\"14\""
				+ "    }"
				+ "}";		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/appointment/book/1")
				.accept(MediaType.APPLICATION_JSON).content(appData)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockAppointmentMvc.perform(requestBuilder).andReturn();
		Appointment actualResponse = mapper.readValue(result.getResponse().getContentAsString(), Appointment.class);
		assertEquals(appt, actualResponse);
	}
}
