package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.edu.fju.miniclinic.model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class StatsController {

    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private AppointmentRepository appointmentRepo;

    // 網頁版統計
    @GetMapping("/stats")
    public String showStats(Model model) {
        model.addAttribute("doctorCount", doctorRepo.count());
        model.addAttribute("patientCount", patientRepo.count());
        model.addAttribute("appointmentCount", appointmentRepo.count());
        model.addAttribute("deptStats", appointmentRepo.countByDepartment());
        return "stats";
    }

    // API 版統計
    @GetMapping("/api/appointments/count")
    @ResponseBody
    public Map<String, Long> getAppointmentCount() {
        return Map.of("count", appointmentRepo.count());
    }

    // API 篩選掛號
    @GetMapping("/api/appointments")
    @ResponseBody
    public List<Appointment> getAppointments(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String doctorId) {
        
        if (date != null && !date.isBlank()) {
            return appointmentRepo.findByApptDate(LocalDate.parse(date));
        } else if (doctorId != null && !doctorId.isBlank()) {
            Doctor doctor = doctorRepo.findById(doctorId).orElse(null);
            if (doctor != null) return appointmentRepo.findByDoctor(doctor);
            return List.of();
        }
        return appointmentRepo.findAll();
    }
}