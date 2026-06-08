package tw.edu.fju.miniclinic.dto;

import java.util.Map;

public class StatsResponse {
    private long totalDoctors;
    private long totalPatients;
    private long totalAppointments;
    private Map<String, Long> byDepartment;

    public StatsResponse(long totalDoctors, long totalPatients, long totalAppointments, Map<String, Long> byDepartment) {
        this.totalDoctors = totalDoctors;
        this.totalPatients = totalPatients;
        this.totalAppointments = totalAppointments;
        this.byDepartment = byDepartment;
    }

    public long getTotalDoctors() { return totalDoctors; }
    public long getTotalPatients() { return totalPatients; }
    public long getTotalAppointments() { return totalAppointments; }
    public Map<String, Long> getByDepartment() { return byDepartment; }
}