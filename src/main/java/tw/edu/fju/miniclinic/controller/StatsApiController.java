package tw.edu.fju.miniclinic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.fju.miniclinic.dto.StatsResponse;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.PatientRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
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

    @GetMapping
    public ResponseEntity<StatsResponse> getStats() {
        long totalDoctors = doctorRepo.count();
        long totalPatients = patientRepo.count();
        long totalAppointments = appointmentRepo.count();

        List<Object[]> queryResult = appointmentRepo.countByDepartment();
        
        Map<String, Long> byDepartment = new LinkedHashMap<>();
        if (queryResult != null) {
            for (Object[] row : queryResult) {
                String deptName = (String) row[0];
                Long count = (Long) row[1];
                if (deptName != null) {
                    byDepartment.put(deptName, count);
                }
            }
        }

        StatsResponse response = new StatsResponse(
                totalDoctors, 
                totalPatients, 
                totalAppointments, 
                byDepartment
        );

        return ResponseEntity.ok(response);
    }
}