package tw.edu.fju.miniclinic.controller;

import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;

import java.time.LocalDate;

@Controller
public class DashboardController {

    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private AppointmentRepository appointmentRepo;

    @GetMapping("/dashboard")
    public String showDashboard(
            @RequestParam(required = false) String chartNo, 
            HttpSession session, Model model) {
        
        String loggedInId = (String) session.getAttribute("loggedInDoctorId");
        Doctor doctor = doctorRepo.findById(loggedInId).orElse(null);

        if (doctor == null) {
            session.invalidate();
            return "redirect:/login";
        }

        LocalDate today = LocalDate.now();
        model.addAttribute("doctor", doctor);
        model.addAttribute("today", today);

       
        if (chartNo != null && !chartNo.isBlank()) {
            model.addAttribute("appointments", appointmentRepo.findByDoctorAndPatient_ChartNo(doctor, chartNo));
            model.addAttribute("searchChartNo", chartNo); 
        } else {
            model.addAttribute("appointments", appointmentRepo.findByDoctorAndApptDate(doctor, today));
        }

        return "dashboard";
    }
}