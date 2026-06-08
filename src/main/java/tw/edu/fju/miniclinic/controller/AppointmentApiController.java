package tw.edu.fju.miniclinic.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.fju.miniclinic.model.Appointment;
import tw.edu.fju.miniclinic.model.AppointmentRepository;

import java.util.List;
import java.util.Map;

@RestController
public class AppointmentApiController {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @PutMapping("/api/appointments/{apptId}/status")
    public ResponseEntity<Appointment> updateStatus(@PathVariable Long apptId, @RequestBody Map<String, String> payload, HttpSession session) {
        String loggedInId = (String) session.getAttribute("loggedInDoctorId");

        Appointment appt = appointmentRepo.findById(apptId).orElse(null);
        if (appt == null) return ResponseEntity.notFound().build();

        if (!appt.getDoctor().getDoctorId().equals(loggedInId)) {
            return ResponseEntity.status(403).build();
        }

        String status = payload.get("status");
        if (!List.of("BOOKED", "COMPLETED", "CANCELLED").contains(status)) {
            return ResponseEntity.badRequest().build();
        }

        appt.setStatus(status);
        return ResponseEntity.ok(appointmentRepo.save(appt));
    }
}