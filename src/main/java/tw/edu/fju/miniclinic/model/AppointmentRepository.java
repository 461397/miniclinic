package tw.edu.fju.miniclinic.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByApptDate(LocalDate apptDate);
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByPatient(Patient patient);
    List<Appointment> findByDoctorAndPatient_ChartNo(Doctor doctor, String chartNo);
    List<Appointment> findByDoctorAndApptDate(Doctor doctor, LocalDate apptDate);
    long countByStatus(String status);

    @Query("SELECT a.doctor.department, COUNT(a) FROM Appointment a GROUP BY a.doctor.department")
    List<Object[]> countByDepartment();
}