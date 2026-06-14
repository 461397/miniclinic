package tw.edu.fju.miniclinic.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.PatientRepository;

@RestController
public class StatsApiController {

    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;
    private final AppointmentRepository appointmentRepo;

    public StatsApiController(DoctorRepository doctorRepo, 
                              PatientRepository patientRepo, 
                              AppointmentRepository appointmentRepo) {
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
        this.appointmentRepo = appointmentRepo;
    }

    @GetMapping("/api/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        
        // 1. 總數統計
        stats.put("totalDoctors", doctorRepo.count());
        stats.put("totalPatients", patientRepo.count());
        stats.put("totalAppointments", appointmentRepo.count());

        // 2. 依狀態統計
        Map<String, Long> byStatus = new LinkedHashMap<>();
        byStatus.put("BOOKED", appointmentRepo.countByStatus("BOOKED"));
        byStatus.put("COMPLETED", appointmentRepo.countByStatus("COMPLETED"));
        byStatus.put("CANCELLED", appointmentRepo.countByStatus("CANCELLED"));
        
        stats.put("byStatus", byStatus);

        return stats; 
    }
}