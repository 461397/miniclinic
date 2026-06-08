package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.edu.fju.miniclinic.model.*;

import java.time.LocalDate;

@Controller
public class AppointmentController {

    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private AppointmentRepository appointmentRepo;

    @GetMapping("/appointment/new")
    public String newAppointmentForm(Model model) {
        model.addAttribute("form", new AppointmentForm());
        model.addAttribute("doctors", doctorRepo.findAll());
        return "appointment-new";
    }

    @PostMapping("/appointment/new")
    public String submitAppointment(@ModelAttribute AppointmentForm form, Model model) {
       
        Patient patient = patientRepo.findById(form.getChartNo()).orElse(null);
        Doctor doctor = doctorRepo.findById(form.getDoctorId()).orElse(null);

       
        if (patient == null || doctor == null) {
            model.addAttribute("error", "查無此病歷號或醫師，請確認後重試");
            model.addAttribute("form", form);
            model.addAttribute("doctors", doctorRepo.findAll());
            return "appointment-new";
        }

        
        Appointment appt = new Appointment();
        appt.setPatient(patient);
        appt.setDoctor(doctor);
        appt.setApptDate(LocalDate.parse(form.getApptDate()));
        appt.setTimeSlot(form.getTimeSlot());
        appt.setStatus("BOOKED");

     
        Appointment saved = appointmentRepo.save(appt);

      
        model.addAttribute("appointment", saved);
        return "appointment-result";
    }
}