package com.revature.project03.controller;

import java.util.ArrayList;
import java.util.List;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project03.entities.Appointment;
import com.revature.project03.entities.DaywiseData;
import com.revature.project03.entities.DoctorLeave;
import com.revature.project03.entities.LoginRoute;
import com.revature.project03.entities.Receptionist;
import com.revature.project03.exception.ResourceNotFoundException;
import com.revature.project03.model.DateFetch;
import com.revature.project03.model.ReceptionistDto;
import com.revature.project03.repository.AppointmentRepository;
import com.revature.project03.service.AppointmentService;
import com.revature.project03.service.DayWiseDataService;
import com.revature.project03.service.DoctorLeaveService;
import com.revature.project03.service.LoginRouteService;
import com.revature.project03.service.ReceptionistService;

import lombok.extern.slf4j.Slf4j;






@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/receptionistController")
public class ReceptionistController {
	
	@Autowired
    private ReceptionistService service;
	@Autowired
	private LoginRouteService loginRouteService;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private DoctorLeaveService doctorLeaveService;
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private DayWiseDataService dayWiseDataService;
	public String generateRandomPassword() {
	    PasswordGenerator gen = new PasswordGenerator();
	    org.passay.CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
	    CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
	    lowerCaseRule.setNumberOfCharacters(2);

	    CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
	    CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
	    upperCaseRule.setNumberOfCharacters(2);

	    CharacterData digitChars = EnglishCharacterData.Digit;
	    CharacterRule digitRule = new CharacterRule(digitChars);
	    digitRule.setNumberOfCharacters(2);

	    CharacterData specialChars = new CharacterData() {
	        public String getErrorCode() {
	            return null;
	        }

	        public String getCharacters() {
	            return "!@#$%^&*()_+";
	        }
	    };
	    CharacterRule splCharRule = new CharacterRule(specialChars);
	    splCharRule.setNumberOfCharacters(2);
	    List l1 = new ArrayList<>();
	    l1.add(splCharRule);
	    l1.add(lowerCaseRule);
	    l1.add(upperCaseRule);
	    l1.add(digitRule);

	    String password = gen.generatePassword(10, l1);
	    return password;
	}
	
	 @PostMapping("/addReceptionist")
	    public Receptionist addReceptionist(@RequestBody Receptionist recep) {
		 String newPass = generateRandomPassword();
		 LoginRoute loginRoute = new LoginRoute();
		 System.out.println(recep.getREmail());
		 recep.setRPassword(newPass);
		 loginRoute.setPasswd(newPass);
		 loginRoute.setRole("receptionist");
		 loginRoute.setUserEmail(recep.getREmail());
		 loginRouteService.saveLoginInfo(loginRoute);
		 log.info("added new receptionist");
	        return service.saveReceptionist(recep);
	    }
	 
	 //---------------------------APPLICATION STATUS----------------------------------------------------------------------------------------------------------
	 
	 @PostMapping("/confirmAppointment/{patientId}")
	 public Appointment confirmAppointment(@RequestBody Appointment appointment,@PathVariable (value = "patientid") Integer patientId) throws ResourceNotFoundException {
		 log.trace("confirm Appointment with patient Id");
		 return appointmentService.confirmAppointment(appointment, patientId);
	 }
	 
	 @PostMapping("/consultingnow/{patientId}")
	 public Appointment consultingnow(@RequestBody Appointment appointment,@PathVariable (value = "patientid") Integer patientId) throws ResourceNotFoundException {
		 log.info("consulting now with patient");
		 return appointmentService.currentlyConsulting(appointment, patientId);
	 }
	 
	 @PostMapping("/completedAppointment/{patientId}/{amount}")
	 public Appointment completedAppointment(@RequestBody Appointment appointment,@PathVariable (value = "patientId") Integer patientId,@PathVariable (value="amount") Integer amount) throws ResourceNotFoundException {
		 if(dayWiseDataService.checkdata(appointment.getApplicationDate(), appointment.getDoctor().getDoctorId())) {
			 DaywiseData dayWise1 = dayWiseDataService.getDataByIdAndDate(appointment.getApplicationDate(), appointment.getDoctor().getDoctorId());
			 log.trace("daily ammount collected daywise :"+amount);
			 dayWiseDataService.updateData(dayWise1, amount);	 
		 }
		 else {
			 DaywiseData daywise2 = new DaywiseData();
			 daywise2.setDate(appointment.getApplicationDate());
			 daywise2.setDocId(appointment.getDoctor().getDoctorId());
			 daywise2.setDocName(appointment.getDoctor().getFirstName());
			 daywise2.setTotalPatients(1);
			 daywise2.setAmountCollected(amount);
			 log.trace("Daywise data added :"+appointment.getApplicationDate());
			 dayWiseDataService.addData(daywise2);
		 }
		 return appointmentService.completedConsulting(appointment, patientId);
	 }
	 @PostMapping("/cancelAppointment/{patientId}")
	 public Appointment cancelAppointment(@RequestBody Appointment appointment,@PathVariable (value = "patientid") Integer patientId) throws ResourceNotFoundException {
		 log.info("cancel Appointment by patient id:");
		 return appointmentService.cancellingAppointment(appointment, patientId);
	 }
	 //---------------------------------------------------------------------------------------------------------------------------------------------------
	 
	 @PostMapping("/cancelAllAppointments")
	 public List<Appointment> cancelAllAppointments(@RequestBody Appointment appointment){
		 List<Appointment> appointments = appointmentService.getAppointmentByDate(appointment.getApplicationDate());
		 List<DoctorLeave> doctorLeaves = doctorLeaveService.findbydates(appointment.getApplicationDate());
		 for(DoctorLeave doctorL:doctorLeaves) {
			 for(Appointment appointmentL:appointments) {
				 if(doctorL.getLeaveDate().compareTo(appointmentL.getApplicationDate())== 0) {
					 appointmentL.setApplicationDate(appointmentL.getApplicationDate());
					 appointmentL.setApplicationId(appointmentL.getApplicationId());
					 appointmentL.setDoctor(appointmentL.getDoctor());
					 appointmentL.setMember(appointmentL.getMember());
					 appointmentL.setPurpose(appointmentL.getPurpose());
					 appointmentL.setAvailability("cancelled");
					 log.trace("cancelled all Appointments");	 
					 appointmentRepository.save(appointmentL);
				 }
			 }
		 }
		 
		 
		 
		return appointments;
		 
	 }
	 @PostMapping("/gettotalNumbers")
	 public List<DaywiseData> getalldatabyDate(@RequestBody DateFetch date){
		 log.info("total numbers of patient data");
		return dayWiseDataService.findbydate(date.getDate());
	 }
	 
}
