package tw.edu.fju.miniclinic.model;

public class AppointmentForm {
    private String chartNo;
    private String doctorId;
    private String apptDate;
    private String timeSlot;

    public AppointmentForm() {}

    public String getChartNo() { return chartNo; }
    public void setChartNo(String chartNo) { this.chartNo = chartNo; }
    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public String getApptDate() { return apptDate; }
    public void setApptDate(String apptDate) { this.apptDate = apptDate; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
}